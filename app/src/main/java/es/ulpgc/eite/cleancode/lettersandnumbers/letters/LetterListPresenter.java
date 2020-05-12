package es.ulpgc.eite.cleancode.lettersandnumbers.letters;

import android.util.Log;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.cleancode.lettersandnumbers.app.LettersToNumbersState;
import es.ulpgc.eite.cleancode.lettersandnumbers.app.NumbersToLettersState;
import es.ulpgc.eite.cleancode.lettersandnumbers.data.LetterData;

public class LetterListPresenter implements LetterListContract.Presenter {

  public static String TAG = LetterListPresenter.class.getSimpleName();

  private WeakReference<LetterListContract.View> view;
  private LetterListState state;
  private LetterListContract.Model model;
  private LetterListContract.Router router;

  public LetterListPresenter(LetterListState state) {
    this.state = state;
  }

  @Override
  public void onStart() {
    // Log.e(TAG, "onStart()");

    // initialize the state if is necessary
    if (state == null) {
      state = new LetterListState();
    }

    /*
    // use passed state if is necessary
    LetterListState savedState = router.getStateFromPreviousScreen();
    if (savedState != null) {

      // update the model if is necessary
      model.onDataFromPreviousScreen(savedState.data);
    }
    */

  }

  @Override
  public void onRestart() {
    // Log.e(TAG, "onRestart()");

    // update the model if is necessary

    model.onRestartScreen(state.data);
    view.get().onDataUpdated(state);
  }

  @Override
  public void onResume() {
    // Log.e(TAG, "onResume()");

    // use passed state if is necessary
    NumbersToLettersState savedState = router.getStateFromNextScreen();
    if (savedState != null) {
      Log.d("holas", savedState.data);
      // update the model if is necessary
      model.onDataFromNextScreen(savedState.data);
    }


    // call the model and update the state
    if(state.data != null)
    Log.d("hola", state.data);
    state.data = model.getStoredData();
    Log.d("hola", state.data);

    // update the view
    view.get().onDataUpdated(state);

  }

  @Override
  public void onBackPressed() {
    // Log.e(TAG, "onBackPressed()");
  }

  @Override
  public void onPause() {
    // Log.e(TAG, "onPause()");
  }

  @Override
  public void onDestroy() {
    // Log.e(TAG, "onDestroy()");
  }

  @Override
  public void onClickLetterListCell(LetterData data) {
    // Log.e(TAG, "onClickLetterListCell()");
    LettersToNumbersState pasadoDeLetra= new LettersToNumbersState();
    pasadoDeLetra.data = state.data;
    router.passStateToNextScreen(pasadoDeLetra);
    view.get().navigateToNextScreen();
  }

  @Override
  public void onClickLetterListButton() {
    // Log.e(TAG, "onClickLetterListButton()");
    LetterData letra = new LetterData();
    switch(state.data){
      case "A" :
        state.data = "B";
        break;
      case "B":
        state.data = "C";
        break;
      case "C":
        state.data = "D";
        break;
      case "D":
        state.data = "E";
        break;
      default:
        state.data = "A";
      }

    letra.letter = state.data;
    state.datasource.add(letra);
    view.get().onDataUpdated(state);
  }

  @Override
  public void injectView(WeakReference<LetterListContract.View> view) {
    this.view = view;
  }

  @Override
  public void injectModel(LetterListContract.Model model) {
    this.model = model;
  }

  @Override
  public void injectRouter(LetterListContract.Router router) {
    this.router = router;
  }
}
