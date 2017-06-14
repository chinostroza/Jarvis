package cl.multicaja.testcode.data.repository;

import cl.multicaja.testcode.data.net.RestApi;
import cl.multicaja.testcode.domain.entity.NoteEntity;
import cl.multicaja.testcode.domain.entity.UserEntity;
import cl.multicaja.testcode.domain.entity.VoidEntity;
import cl.multicaja.testcode.domain.repository.NoteRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import retrofit2.Response;

@Singleton
public class NoteDataRepository extends RestApiRepository implements NoteRepository {

    private final RestApi restApi;

    @Inject
    public NoteDataRepository(RestApi restApi) {
        this.restApi = restApi;
    }

    @Override
    public Observable<NoteEntity> createNote(UserEntity user, final NoteEntity note) {
        return this.restApi.createNote(user.getAuthToken(), note)
                .map(noteEntityResponse -> {
                    handleResponseError(noteEntityResponse);
                    return noteEntityResponse.body();
                });
    }

    @Override
    public Observable<NoteEntity> getNote(UserEntity user, int noteId) {
        return this.restApi.getNote(user.getAuthToken(), noteId)
                .map(noteEntityResponse -> {
                    handleResponseError(noteEntityResponse);
                    return noteEntityResponse.body();
                });
    }

    @Override
    public Observable<List<NoteEntity>> getNotes(UserEntity user) {
        return this.restApi.getNotes(user.getAuthToken())
                .map(listResponse -> {
                    handleResponseError(listResponse);
                    return listResponse.body();
                });
    }

    @Override
    public Observable<NoteEntity> updateNote(UserEntity user, NoteEntity note) {
        return this.restApi.updateNote(user.getAuthToken(), note.getId(), note)
                .map(noteEntityResponse -> {
                    handleResponseError(noteEntityResponse);
                    return noteEntityResponse.body();
                });
    }

    @Override
    public Observable<VoidEntity> deleteNote(UserEntity user, int noteId) {
        return this.restApi.deleteNote(user.getAuthToken(), noteId)
                .map(response -> {
                    handleResponseError(response);
                    return new VoidEntity();
                });
    }
}
