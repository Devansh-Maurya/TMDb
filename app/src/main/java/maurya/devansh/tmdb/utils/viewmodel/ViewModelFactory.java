package maurya.devansh.tmdb.utils.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;

import javax.inject.Provider;

/**
 * Created by devansh on 17/07/21.
 */

public class ViewModelFactory implements ViewModelProvider.Factory {

    private Map<Class<? extends ViewModel>, Provider<ViewModel>> viewModelMap;

    ViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> viewModelMap) {
        viewModelMap = this.viewModelMap;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) viewModelMap.get(modelClass).get();
    }
}