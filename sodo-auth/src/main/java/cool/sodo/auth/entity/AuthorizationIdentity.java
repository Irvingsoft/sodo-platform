package cool.sodo.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationIdentity implements Serializable {

    private String token;
    private String redirectUri;
    private Date expireAt;
}
