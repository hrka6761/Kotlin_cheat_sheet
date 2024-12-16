package ir.hrka.kotlin.ui.screens.coroutine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hrka.kotlin.core.ExecutionState
import ir.hrka.kotlin.core.ExecutionState.Start
import ir.hrka.kotlin.core.utilities.Resource
import ir.hrka.kotlin.core.utilities.extractMajorFromVersionName
import ir.hrka.kotlin.core.utilities.extractMinorFromVersionName
import ir.hrka.kotlin.core.utilities.extractPatchFromVersionName
import ir.hrka.kotlin.core.utilities.extractUpdatedKotlinTopicsListFromVersionName
import ir.hrka.kotlin.domain.entities.db.CoroutineTopic
import ir.hrka.kotlin.domain.entities.db.KotlinTopic
import ir.hrka.kotlin.domain.usecases.db.coroutine.read.GetDBCoroutineTopicsListUseCase
import ir.hrka.kotlin.domain.usecases.db.coroutine.write.ClearCoroutineTopicsTableUseCase
import ir.hrka.kotlin.domain.usecases.db.coroutine.write.SaveCoroutineTopicsOnDBUseCase
import ir.hrka.kotlin.domain.usecases.db.coroutine.write.UpdateCoroutineTopicsStateUseCase
import ir.hrka.kotlin.domain.usecases.git.coroutine.read.GetGitCoroutineTopicsListUseCase
import ir.hrka.kotlin.domain.usecases.preference.LoadCurrentVersionNameUseCase
import ir.hrka.kotlin.domain.usecases.preference.SaveCurrentVersionNameUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class CoroutineTopicsViewModel @Inject constructor(
    @Named("IO") private val io: CoroutineDispatcher,
    private val getGitCoroutineTopicsListUseCase: GetGitCoroutineTopicsListUseCase,
    private val getDBKotlinTopicsListUseCase: GetDBCoroutineTopicsListUseCase,
    private val loadCurrentVersionNameUseCase: LoadCurrentVersionNameUseCase,
    private val saveCurrentVersionNameUseCase: SaveCurrentVersionNameUseCase,
    private val clearCoroutineTopicsTableUseCase: ClearCoroutineTopicsTableUseCase,
    private val saveCoroutineTopicsOnDBUseCase: SaveCoroutineTopicsOnDBUseCase,
    private val updateCoroutineTopicsStateUseCase: UpdateCoroutineTopicsStateUseCase
) : ViewModel() {

    private val _coroutineTopics: MutableStateFlow<Resource<List<CoroutineTopic>?>> =
        MutableStateFlow(Resource.Initial())
    val coroutineTopics: StateFlow<Resource<List<CoroutineTopic>?>> = _coroutineTopics
    private val _executionState: MutableStateFlow<ExecutionState> = MutableStateFlow(Start)
    val executionState: MutableStateFlow<ExecutionState> = _executionState
    private val _hasUpdateForCoroutineTopicsList: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val hasUpdateForCoroutineTopicsList: StateFlow<Boolean?> = _hasUpdateForCoroutineTopicsList
    private val _hasUpdateForCoroutineTopicsContent: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val hasUpdateForCoroutineTopicsContent: StateFlow<Boolean?> = _hasUpdateForCoroutineTopicsContent
    private val _saveCoroutineTopicsListResult: MutableStateFlow<Resource<Boolean>> =
        MutableStateFlow(Resource.Initial())
    val saveCoroutineTopicsListResult: StateFlow<Resource<Boolean>> = _saveCoroutineTopicsListResult
    private val _updateCoroutineTopicsOnDBResult: MutableStateFlow<Resource<Boolean>> =
        MutableStateFlow(Resource.Initial())
    val updateCoroutineTopicsOnDBResult: MutableStateFlow<Resource<Boolean>> =
        _updateCoroutineTopicsOnDBResult
    private lateinit var currentVersionName: String
    private val _failedState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val failedState: StateFlow<Boolean> = _failedState


    fun setExecutionState(state: ExecutionState) {
        _executionState.value = state
    }

    fun setFailedState(state: Boolean) {
        _failedState.value = state
    }

    fun getCoroutineTopicsFromGithub() {
        viewModelScope.launch(io) {
            _coroutineTopics.value = Resource.Loading()
            _coroutineTopics.value = getGitCoroutineTopicsListUseCase()
        }
    }

    fun getCoroutineTopicsFromDatabase() {
        viewModelScope.launch(io) {
            _coroutineTopics.value = Resource.Loading()
            _coroutineTopics.value = getDBKotlinTopicsListUseCase()
        }
    }

    fun checkNewUpdateForCoroutineTopicsList(gitVersionName: String?) {
        viewModelScope.launch(io) {
            currentVersionName =
                (loadCurrentVersionNameUseCase().data ?: "0.0.0").ifEmpty { "0.0.0" }

            if (gitVersionName.isNullOrEmpty()) {
                _hasUpdateForCoroutineTopicsList.value = false
                return@launch
            }

            val currentVersionMajorDiffered =
                async { currentVersionName.extractMajorFromVersionName() }
            val githubVersionMajorDiffered =
                async { gitVersionName.extractMajorFromVersionName() }

            val githubVersionMajor = githubVersionMajorDiffered.await()
            val currentVersionMajor = currentVersionMajorDiffered.await()

            if (githubVersionMajor != currentVersionMajor) {
                _hasUpdateForCoroutineTopicsList.value = true
                return@launch
            }

            val currentVersionMinorDiffered =
                async { currentVersionName.extractMinorFromVersionName() }
            val githubVersionMinorDiffered =
                async { gitVersionName.extractMinorFromVersionName() }

            val githubVersionMinor = githubVersionMinorDiffered.await()
            val currentVersionMinor = currentVersionMinorDiffered.await()

            if (githubVersionMinor != currentVersionMinor) {
                _hasUpdateForCoroutineTopicsList.value = true
                return@launch
            }

            _hasUpdateForCoroutineTopicsList.value = false
        }
    }

    fun checkNewUpdateForCoroutineTopicsContent(gitVersionName: String?) {
        viewModelScope.launch(io) {
            if (gitVersionName.isNullOrEmpty()) {
                _hasUpdateForCoroutineTopicsContent.value = false
                return@launch
            }

            val currentVersionPatchDiffered =
                async { currentVersionName.extractPatchFromVersionName() }
            val githubVersionPatchDiffered =
                async { gitVersionName.extractPatchFromVersionName() }

            val githubVersionPatch = githubVersionPatchDiffered.await()
            val currentVersionPatch = currentVersionPatchDiffered.await()

            if (githubVersionPatch != currentVersionPatch) {
                if ((githubVersionPatch - currentVersionPatch) > 1) {
                    _hasUpdateForCoroutineTopicsList.value = true
                    return@launch
                }
                _hasUpdateForCoroutineTopicsContent.value = true
                return@launch
            }

            _hasUpdateForCoroutineTopicsContent.value = false
        }
    }

    fun updateCoroutineTopicsInDatabase(gitVersionSuffix: String?) {
        viewModelScope.launch(io) {
            val updatedKotlinTopicsList =
                gitVersionSuffix!!.extractUpdatedKotlinTopicsListFromVersionName()

            updateCoroutineTopicsOnDBResult.value = updateCoroutineTopicsStateUseCase(
                *updatedKotlinTopicsList.toIntArray(),
                hasContentUpdated = true
            )
        }
    }

    fun saveCoroutineTopicsOnDB(gitVersionName: String) {
        viewModelScope.launch(io) {
            val clearDiffered = async { clearCoroutineTopicsTableUseCase() }
            val clearResult = clearDiffered.await()

            if (clearResult is Resource.Error) {
                _saveCoroutineTopicsListResult.value = clearResult
                return@launch
            }

            _coroutineTopics.value.data?.map { kotlinTopic ->
                KotlinTopic(
                    id = kotlinTopic.id,
                    name = kotlinTopic.name,
                    versionName = gitVersionName
                )
            }?.let {
                val saveDiffered = async { saveCoroutineTopicsOnDBUseCase(it) }
                _saveCoroutineTopicsListResult.value = saveDiffered.await()
            }
        }
    }

    fun saveVersionName(gitVersionName: String) {
        viewModelScope.launch(io) {
            saveCurrentVersionNameUseCase(gitVersionName)
        }
    }

    fun updateCoroutineTopicsList(id: Int) {
        _coroutineTopics.value.data?.get(id)?.hasUpdated = false
    }
}