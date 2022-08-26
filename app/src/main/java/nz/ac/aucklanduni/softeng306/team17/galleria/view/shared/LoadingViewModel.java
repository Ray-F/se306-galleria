package nz.ac.aucklanduni.softeng306.team17.galleria.view.shared;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class LoadingViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);

    private final Set<UUID> loadingRequests = new HashSet<>();

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    protected UUID setIsLoading() {
        UUID id = UUID.randomUUID();
        loadingRequests.add(id);
        this.isLoading.setValue(true);

        return id;
    }

    protected void setIsLoaded(UUID uuid) {
        System.out.println(uuid);
        loadingRequests.remove(uuid);
        System.out.println(loadingRequests.size());

        if (loadingRequests.isEmpty()) {
            isLoading.setValue(false);
        }

    }
}
