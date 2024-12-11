package ir.hrka.kotlin.ui.screens.points

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hrka.kotlin.core.ExecutionState
import ir.hrka.kotlin.core.ExecutionState.Start
import ir.hrka.kotlin.core.utilities.Resource
import ir.hrka.kotlin.domain.entities.KotlinTopicPointDataModel
import ir.hrka.kotlin.domain.usecases.db.read.GetDBKotlinTopicPointsUseCase
import ir.hrka.kotlin.domain.usecases.github.GetGithubKotlinTopicPointsUseCase
import ir.hrka.kotlin.domain.usecases.db.write.SaveKotlinTopicPointsOnDBUseCase
import ir.hrka.kotlin.domain.usecases.db.write.UpdateKotlinTopicsUpdateStateUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class PointViewModel @Inject constructor(
    @Named("IO") private val io: CoroutineDispatcher,
    private val getGithubCheatSheetPointsUseCase: GetGithubKotlinTopicPointsUseCase,
    private val getDBCheatSheetPointsUseCase: GetDBKotlinTopicPointsUseCase,
    private val saveCheatsheetPointsOnDBUseCase: SaveKotlinTopicPointsOnDBUseCase,
    private val updateCheatSheetUpdateStateUseCase: UpdateKotlinTopicsUpdateStateUseCase
) : ViewModel() {

    private val _points: MutableStateFlow<Resource<List<KotlinTopicPointDataModel>?>> =
        MutableStateFlow(Resource.Initial())
    val points: StateFlow<Resource<List<KotlinTopicPointDataModel>?>> = _points
    private val _executionState: MutableStateFlow<ExecutionState> = MutableStateFlow(Start)
    val executionState: MutableStateFlow<ExecutionState> = _executionState
    private val _saveCheatsheetPointsResult: MutableStateFlow<Resource<Boolean>> =
        MutableStateFlow(Resource.Initial())
    val saveCheatsheetPointsResult: MutableStateFlow<Resource<Boolean>> =
        _saveCheatsheetPointsResult
    private val _updateCheatsheetsOnDBResult: MutableStateFlow<Resource<Boolean>> =
        MutableStateFlow(Resource.Initial())
    val updateCheatsheetsOnDBResult: MutableStateFlow<Resource<Boolean>> =
        _updateCheatsheetsOnDBResult
    private val _failedState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val failedState: StateFlow<Boolean> = _failedState


    fun setExecutionState(state: ExecutionState) {
        _executionState.value = state
    }

    fun setFailedState(state: Boolean) {
        _failedState.value = state
    }

    fun getPointsFromGithub(fileName: String) {
        viewModelScope.launch(io) {
            _points.value = Resource.Loading()
            _points.value = getGithubCheatSheetPointsUseCase(fileName)
        }
    }

    fun getPointsFromDatabase(fileName: String) {
        viewModelScope.launch(io) {
            _points.value = Resource.Loading()
            _points.value = getDBCheatSheetPointsUseCase(fileName)
        }
    }

    fun saveCheatsheetPointsOnDB(cheatsheetName: String) {
        viewModelScope.launch(io) {
            _saveCheatsheetPointsResult.value =
                _points.value.data?.let { saveCheatsheetPointsOnDBUseCase(it, cheatsheetName) }!!
        }
    }

    fun updateCheatsheetState(cheatsheetId: Int) {
        viewModelScope.launch(io) {
            _updateCheatsheetsOnDBResult.value =
                updateCheatSheetUpdateStateUseCase(cheatsheetId, hasContentUpdated = false)
        }
    }
}