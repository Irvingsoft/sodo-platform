package cool.sodo.common.base.service;

public interface CommonClientApiService {

    boolean validateClientApi(String clientId, String apiId);

    int countClientApiByClientAndApi(String clientId, String apiId);
}
