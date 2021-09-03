package cool.sodo.housekeeper;

import cool.sodo.common.entity.Constants;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class AccessTokenServiceImplTest {

    @CacheEvict(cacheNames = Constants.ACCESS_TOKEN_CACHE_NAME, key = "#token")
    public void delete(String token) {

    }


}
