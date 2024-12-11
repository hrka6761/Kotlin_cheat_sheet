package ir.hrka.kotlin.ui.screens.cheatsheets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hrka.kotlin.core.ExecutionState
import ir.hrka.kotlin.core.ExecutionState.Start
import ir.hrka.kotlin.core.utilities.Resource
import ir.hrka.kotlin.core.utilities.extractMajorFromVersionName
import ir.hrka.kotlin.core.utilities.extractMinorFromVersionName
import ir.hrka.kotlin.core.utilities.extractPatchFromVersionName
import ir.hrka.kotlin.core.utilities.extractUpdatedCheatsheetsListFromVersionName
import ir.hrka.kotlin.domain.entities.db.KotlinTopicModel
import ir.hrka.kotlin.domain.usecases.db.write.ClearKotlinTopicsTableUseCase
import ir.hrka.kotlin.domain.usecases.db.read.GetDBKotlinTopicsListUseCase
import ir.hrka.kotlin.domain.usecases.github.GetGithubKotlinTopicsListUseCase
import ir.hrka.kotlin.domain.usecases.preference.LoadCurrentVersionNameUseCase
import ir.hrka.kotlin.domain.usecases.db.write.SaveKotlinTopicsOnDBUseCase
import ir.hrka.kotlin.domain.usecases.preference.SaveCurrentVersionNameUseCase
import ir.hrka.kotlin.domain.usecases.db.write.UpdateKotlinTopicsUpdateStateUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class CheatSheetsViewModel @Inject constructor(
    @Named("IO") private val io: CoroutineDispatcher,
    private val getGithubCheatSheetsUseCase: GetGithubKotlinTopicsListUseCase,
    private val getDBCheatsheetsUseCase: GetDBKotlinTopicsListUseCase,
    private val loadCurrentVersionNameUseCase: LoadCurrentVersionNameUseCase,
    private val saveCurrentVersionNameUseCase: SaveCurrentVersionNameUseCase,
    private val clearCheatsheetTableUseCase: ClearKotlinTopicsTableUseCase,
    private val saveCheatsheetsOnDBUseCase: SaveKotlinTopicsOnDBUseCase,
    private val updateCheatSheetUpdateStateUseCase: UpdateKotlinTopicsUpdateStateUseCase
) : ViewModel() {

    private val _cheatSheets: MutableStateFlow<Resource<List<KotlinTopicModel>?>> =
        MutableStateFlow(Resource.Initial())
    val cheatSheets: StateFlow<Resource<List<KotlinTopicModel>?>> = _cheatSheets
    private val _executionState: MutableStateFlow<ExecutionState> = MutableStateFlow(Start)
    val executionState: MutableStateFlow<ExecutionState> = _executionState
    private val _hasUpdateForCheatSheetsList: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val hasUpdateForCheatSheetsList: StateFlow<Boolean?> = _hasUpdateForCheatSheetsList
    private val _hasUpdateForCheatSheetsContent: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val hasUpdateForCheatSheetsContent: StateFlow<Boolean?> = _hasUpdateForCheatSheetsContent
    private val _saveCheatsheetsListResult: MutableStateFlow<Resource<Boolean>> =
        MutableStateFlow(Resource.Initial())
    val saveCheatsheetsListResult: StateFlow<Resource<Boolean>> = _saveCheatsheetsListResult
    private val _updateCheatsheetsOnDBResult: MutableStateFlow<Resource<Boolean>> =
        MutableStateFlow(Resource.Initial())
    val updateCheatsheetsOnDBResult: MutableStateFlow<Resource<Boolean>> =
        _updateCheatsheetsOnDBResult
    private lateinit var currentVersionName: String
    private val _failedState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val failedState: StateFlow<Boolean> = _failedState

    fun setExecutionState(state: ExecutionState) {
        _executionState.value = state
    }

    fun setFailedState(state: Boolean) {
        _failedState.value = state
    }

    fun getCheatSheetsFromGithub() {
        viewModelScope.launch(io) {
            _cheatSheets.value = Resource.Loading()
            _cheatSheets.value = getGithubCheatSheetsUseCase()
        }
    }

    fun getCheatSheetFromDatabase() {
        viewModelScope.launch(io) {
            _cheatSheets.value = Resource.Loading()
            _cheatSheets.value = getDBCheatsheetsUseCase()
        }
    }

    fun checkNewUpdateForCheatsheetsList(githubVersionName: String?) {
        viewModelScope.launch(io) {
            currentVersionName =
                (loadCurrentVersionNameUseCase().data ?: "0.0.0").ifEmpty { "0.0.0" }

            if (githubVersionName.isNullOrEmpty()) {
                _hasUpdateForCheatSheetsList.value = false
                return@launch
            }

            val currentVersionMajorDiffered =
                async { currentVersionName.extractMajorFromVersionName() }
            val githubVersionMajorDiffered =
                async { githubVersionName.extractMajorFromVersionName() }

            val githubVersionMajor = githubVersionMajorDiffered.await()
            val currentVersionMajor = currentVersionMajorDiffered.await()

            if (githubVersionMajor != currentVersionMajor) {
                _hasUpdateForCheatSheetsList.value = true
                return@launch
            }

            val currentVersionMinorDiffered =
                async { currentVersionName.extractMinorFromVersionName() }
            val githubVersionMinorDiffered =
                async { githubVersionName.extractMinorFromVersionName() }

            val githubVersionMinor = githubVersionMinorDiffered.await()
            val currentVersionMinor = currentVersionMinorDiffered.await()

            if (githubVersionMinor != currentVersionMinor) {
                _hasUpdateForCheatSheetsList.value = true
                return@launch
            }

            _hasUpdateForCheatSheetsList.value = false
        }
    }

    fun checkNewUpdateForCheatsheetsContent(githubVersionName: String?) {
        viewModelScope.launch(io) {
            if (githubVersionName.isNullOrEmpty()) {
                _hasUpdateForCheatSheetsContent.value = false
                return@launch
            }

            val currentVersionPatchDiffered =
                async { currentVersionName.extractPatchFromVersionName() }
            val githubVersionPatchDiffered =
                async { githubVersionName.extractPatchFromVersionName() }

            val githubVersionPatch = githubVersionPatchDiffered.await()
            val currentVersionPatch = currentVersionPatchDiffered.await()

            if (githubVersionPatch != currentVersionPatch) {
                if ((githubVersionPatch - currentVersionPatch) > 1) {
                    _hasUpdateForCheatSheetsList.value = true
                    return@launch
                }
                _hasUpdateForCheatSheetsContent.value = true
                return@launch
            }

            _hasUpdateForCheatSheetsContent.value = false
        }
    }

    fun updateCheatsheetsInDatabase(githubVersionSuffix: String?) {
        viewModelScope.launch(io) {
            val updatedCheatsheetsList =
                githubVersionSuffix!!.extractUpdatedCheatsheetsListFromVersionName()

            updateCheatsheetsOnDBResult.value = updateCheatSheetUpdateStateUseCase(
                *updatedCheatsheetsList.toIntArray(),
                hasContentUpdated = true
            )
        }
    }

    fun saveCheatsheetsOnDB(githubVersionName: String) {
        viewModelScope.launch(io) {
            val clearDiffered = async { clearCheatsheetTableUseCase() }
            val clearResult = clearDiffered.await()

            if (clearResult is Resource.Error) {
                _saveCheatsheetsListResult.value = clearResult
                return@launch
            }

            _cheatSheets.value.data?.map { cheatsheet ->
                KotlinTopicModel(
                    id = cheatsheet.id,
                    name = cheatsheet.name,
                    versionName = githubVersionName
                )
            }?.let {
                val saveDiffered = async { saveCheatsheetsOnDBUseCase(it) }
                _saveCheatsheetsListResult.value = saveDiffered.await()
            }
        }
    }

    fun saveVersionName(githubVersionName: String) {
        viewModelScope.launch(io) {
            saveCurrentVersionNameUseCase(githubVersionName)
        }
    }

    fun updateCheatSheetsList(id: Int) {
        _cheatSheets.value.data?.get(id)?.hasContentUpdated = false
    }
}