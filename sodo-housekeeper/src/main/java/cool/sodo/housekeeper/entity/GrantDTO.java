package cool.sodo.housekeeper.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GrantDTO implements Serializable {

    private List<String> userIdList;

    private List<String> roleIdList;

    private List<String> menuIdList;
}
