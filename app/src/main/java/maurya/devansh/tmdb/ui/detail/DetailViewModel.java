package maurya.devansh.tmdb.ui.detail;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;

import androidx.core.content.FileProvider;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import maurya.devansh.tmdb.BuildConfig;
import maurya.devansh.tmdb.data.model.MovieDetail;
import maurya.devansh.tmdb.data.repository.MovieRepository;
import maurya.devansh.tmdb.ui.base.BaseViewModel;

/**
 * Created by devansh on 21/07/21.
 */

public class DetailViewModel extends BaseViewModel {

    private final MovieRepository movieRepository;

    public MediatorLiveData<MovieDetail> movieDetailLiveData = new MediatorLiveData<>();

    @Inject
    DetailViewModel(
        CompositeDisposable compositeDisposable,
        MovieRepository movieRepository
    ) {
        super(compositeDisposable);
        this.movieRepository = movieRepository;
        this.movieRepository.setCompositeDisposable(compositeDisposable);
    }

    public void getMovieDetail(int movieId) {
        LiveData<MovieDetail> liveData = movieRepository.getMovieDetail(movieId);
        movieDetailLiveData.addSource(liveData, movieDetailLiveData::setValue);
    }

    public LiveData<String> getShareableViewImageUri(View view) {
        MutableLiveData<String> imageUriLiveData = new MutableLiveData<>();
        compositeDisposable.add(Completable.fromAction(() -> {
                // Draw view to Bitmap
                if (!ViewCompat.isLaidOut(view)) {
                    throw new IllegalStateException("View needs to be laid out before calling drawToBitmap()");
                }

                Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                canvas.translate(-view.getScrollX(), -view.getScaleY());
                view.draw(canvas);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                ByteArrayInputStream ip = new ByteArrayInputStream(stream.toByteArray());
                bitmap.recycle();

                // Save file to directory
                String appCachePath = view.getContext().getCacheDir().getPath();
                String shareableImageFilePath = appCachePath + File.separator + "MovieTempImage.png";
                BufferedOutputStream op = new BufferedOutputStream(new FileOutputStream(shareableImageFilePath));
                int bufferSize = 1024;

                try {
                    byte[] buffer = new byte[bufferSize];
                    int bytes = ip.read(buffer);
                    while (bytes >= 0) {
                        op.write(buffer, 0, bytes);
                        bytes = ip.read(buffer);
                    }
                } finally {
                    try {
                        ip.close();
                        op.close();
                    } catch (Throwable ignored) {
                        Log.e("Share_error", ignored.toString());
                    }
                }

                imageUriLiveData.postValue(
                    FileProvider.getUriForFile(
                        view.getContext(),
                        BuildConfig.AUTHORITY,
                        new File(shareableImageFilePath)).toString()
                );
            }
            ).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(() -> {
                }, throwable -> {
                })
        );
        return imageUriLiveData;
    }
}