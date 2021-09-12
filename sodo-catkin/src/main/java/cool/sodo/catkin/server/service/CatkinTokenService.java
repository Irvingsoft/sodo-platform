package cool.sodo.catkin.server.service;

public interface CatkinTokenService {

    boolean validate(String token, String bizType);
}
