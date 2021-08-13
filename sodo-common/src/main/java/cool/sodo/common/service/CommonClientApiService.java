package cool.sodo.common.service;

public interface CommonClientApiService {

    boolean validateClientApi(String clientId, String apiId);

    int countClientApiByClientAndApi(String clientId, String apiId);
}
