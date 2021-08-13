create table if not exists access_token
(
    token     varchar(64)                         not null
        primary key,
    identity  varchar(64)                         null,
    client_id varchar(64)                         null,
    expire_at timestamp                           null,
    update_at timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
);

create table if not exists address
(
    address_id      varchar(64)      not null
        primary key,
    user_id         varchar(64)      null,
    school_id       varchar(64)      null,
    area_id         varchar(64)      null,
    detail          varchar(128)     null,
    name            varchar(64)      null,
    phone           varchar(18)      null,
    gender          int              null,
    default_address bit default b'0' null,
    create_at       timestamp        null,
    update_at       timestamp        null
);

create table if not exists client_to_api
(
    id        varchar(64)                         not null
        primary key,
    client_id varchar(64)                         null,
    api_id    varchar(128)                        null,
    create_at timestamp default CURRENT_TIMESTAMP null,
    create_by varchar(64)                         null
);

create table if not exists goods
(
    goods_id          varchar(64)                              not null
        primary key,
    shop_id           varchar(64)                              null,
    menu_id           varchar(64)                              null,
    name              varchar(64)                              null,
    avatar_url        varchar(255)                             null,
    weight            int                                      null,
    description       varchar(255)                             null,
    price_description varchar(255)                             null,
    original_price    decimal(10, 2)                           null,
    discount_price    decimal(10, 2)                           null,
    packing_price     decimal(10, 2)                           null,
    discount_limit    int            default 0                 null,
    discount          bit            default b'0'              null,
    score             double(4, 2)   default 0.00              null,
    orders_all        int            default 0                 null,
    orders_month      int            default 0                 null,
    orders_week       int            default 0                 null,
    orders_day        int            default 0                 null,
    income_all        decimal(10, 2) default 0.00              null,
    income_month      decimal(10, 2) default 0.00              null,
    income_week       decimal(10, 2) default 0.00              null,
    income_day        decimal(10, 2) default 0.00              null,
    stock_num         int            default 0                 null,
    stock             bit            default b'0'              null,
    sort              int                                      null,
    sell              bit            default b'0'              null,
    create_at         timestamp      default CURRENT_TIMESTAMP null,
    update_at         timestamp      default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
);

create index goods_id
    on goods (goods_id);

create index menu_id
    on goods (menu_id);

create index shop_id
    on goods (shop_id);

create table if not exists goods_choice
(
    choice_id    varchar(64)                         not null
        primary key,
    shop_id      varchar(64)                         null,
    set_id       varchar(64)                         null,
    name         varchar(64)                         null,
    choice_price decimal(10, 2)                      null,
    sort         int                                 null,
    create_at    timestamp default CURRENT_TIMESTAMP null,
    update_at    timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
);

create table if not exists goods_set
(
    set_id    varchar(64)                         not null
        primary key,
    shop_id   varchar(64)                         null,
    name      varchar(64)                         null,
    sort      int                                 null,
    create_at timestamp default CURRENT_TIMESTAMP null,
    update_at timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
);

create table if not exists goods_to_set
(
    id        varchar(64)                         not null
        primary key,
    goods_id  varchar(64)                         not null,
    set_id    varchar(64)                         not null,
    create_at timestamp default CURRENT_TIMESTAMP null
);

create table if not exists log_api
(
    id              varchar(64)                         null,
    service_id      varchar(64)                         null,
    service_ip      varchar(64)                         null,
    service_host    varchar(64)                         null,
    env             varchar(64)                         null,
    client_id       varchar(64)                         null,
    user_id         varchar(64)                         null,
    request_id      varchar(64)                         null,
    user_ip         varchar(64)                         null,
    system_name     varchar(64)                         null,
    browser_name    varchar(64)                         null,
    request_url     varchar(255)                        null,
    request_method  varchar(64)                         null,
    class_name      varchar(255)                        null,
    method_name     varchar(255)                        null,
    request_body    longblob                            null,
    response_status int                                 null,
    response_body   longblob                            null,
    api_id          varchar(64)                         null,
    time            int                                 null,
    create_at       timestamp default CURRENT_TIMESTAMP null
);

create table if not exists log_business
(
    id             varchar(64)                         null,
    service_id     varchar(64)                         null,
    service_ip     varchar(64)                         null,
    service_host   varchar(64)                         null,
    env            varchar(64)                         null,
    client_id      varchar(64)                         null,
    user_id        varchar(64)                         null,
    request_id     varchar(64)                         null,
    user_ip        varchar(64)                         null,
    system_name    varchar(64)                         null,
    browser_name   varchar(64)                         null,
    request_url    varchar(255)                        null,
    request_method varchar(64)                         null,
    class_name     varchar(255)                        null,
    method_name    varchar(255)                        null,
    business_type  varchar(64)                         null,
    business_id    varchar(64)                         null,
    business_data  longblob                            null,
    message        longblob                            null,
    create_at      timestamp default CURRENT_TIMESTAMP null
);

create table if not exists log_error
(
    id             varchar(64)                         not null
        primary key,
    service_id     varchar(64)                         null,
    service_ip     varchar(64)                         null,
    service_host   varchar(64)                         null,
    env            varchar(64)                         null,
    client_id      varchar(64)                         null,
    user_id        varchar(64)                         null,
    request_id     varchar(64)                         null,
    user_ip        varchar(64)                         null,
    system_name    varchar(64)                         null,
    browser_name   varchar(64)                         null,
    request_url    varchar(255)                        null,
    request_method varchar(64)                         null,
    class_name     varchar(255)                        null,
    method_name    varchar(255)                        null,
    stack_trace    longblob                            null,
    exception_name varchar(255)                        null,
    message        longblob                            null,
    file_name      varchar(255)                        null,
    line_num       int                                 null,
    params         longblob                            null,
    create_at      timestamp default CURRENT_TIMESTAMP null
);

create table if not exists menu
(
    menu_id     varchar(64)                         not null
        primary key,
    parent_id   varchar(64)                         null,
    code        varchar(64)                         null,
    client_id   varchar(64)                         null,
    name        varchar(64)                         null,
    path        varchar(255)                        null,
    icon        varchar(255)                        null,
    description varchar(255)                        null,
    sort        int                                 null,
    menu_type   int                                 null,
    button_type int                                 null,
    new_window  bit       default b'0'              null,
    in_use      bit       default b'1'              null,
    update_at   timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    create_at   timestamp default CURRENT_TIMESTAMP null
);

create table if not exists oauth_api
(
    api_id        varchar(64)                         not null
        primary key,
    name          varchar(64)                         null,
    path          varchar(255)                        null,
    service       varchar(64)                         null,
    method        varchar(64)                         null,
    description   varchar(255)                        null,
    limit_num     int                                 null,
    limit_period  int                                 null,
    request_day   int       default 0                 null,
    request_week  int       default 0                 null,
    request_month int       default 0                 null,
    request_all   int       default 0                 null,
    request_limit bit       default b'0'              null,
    log           bit       default b'0'              null,
    in_use        bit       default b'0'              null,
    auth          bit       default b'0'              null,
    create_at     timestamp default CURRENT_TIMESTAMP null,
    update_at     timestamp                           null,
    create_by     varchar(64)                         null,
    update_by     varchar(64)                         null,
    deleted       bit       default b'0'              null
);

create table if not exists oauth_client
(
    client_id     varchar(64)                         not null
        primary key,
    name          varchar(64)                         null,
    client_secret varchar(64)                         null,
    description   varchar(64)                         null,
    in_use        bit       default b'0'              null,
    register      bit                                 null,
    captcha       bit                                 null,
    user_status   int                                 null,
    update_at     timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    create_by     varchar(64)                         null,
    update_by     varchar(64)                         null,
    deleted       bit       default b'0'              null,
    create_at     timestamp default CURRENT_TIMESTAMP null,
    redirect_uri  varchar(255)                        null,
    token_expire  int                                 null
);

create table if not exists oauth_ip
(
    ip_id       varchar(64)                         not null
        primary key,
    client_id   varchar(64)                         null,
    ip          varchar(64)                         null,
    description varchar(255)                        null,
    valid_num   int       default 0                 null,
    valid       bit                                 null,
    create_by   varchar(64)                         null,
    update_by   varchar(64)                         null,
    create_at   timestamp default CURRENT_TIMESTAMP null,
    update_at   timestamp                           null
);

create table if not exists oauth_user
(
    open_id    varchar(64)                         not null
        primary key,
    union_id   varchar(64)                         null,
    client_id  varchar(64)                         null,
    nick_name  varchar(64)                         null,
    avatar_url varchar(255)                        null,
    phone      varchar(18)                         null,
    gender     int                                 null,
    country    varchar(64)                         null,
    province   varchar(64)                         null,
    city       varchar(64)                         null,
    language   varchar(64)                         null,
    create_at  timestamp default CURRENT_TIMESTAMP null,
    update_at  timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
);

create table if not exists role
(
    role_id     varchar(64)                         not null
        primary key,
    parent_id   varchar(64)                         null,
    client_id   varchar(64)                         null,
    name        varchar(64)                         null,
    alias       varchar(64)                         null,
    description varchar(255)                        null,
    sort        int                                 null,
    create_at   timestamp default CURRENT_TIMESTAMP null,
    update_at   timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment 'c'
);

create table if not exists role_to_menu
(
    id        varchar(64)                         not null
        primary key,
    role_id   varchar(64)                         null,
    menu_id   varchar(64)                         null,
    create_at timestamp default CURRENT_TIMESTAMP null
);

create table if not exists schedule
(
    schedule_id  varchar(64)                         not null
        primary key,
    object_id    varchar(64)                         null,
    open_hour    int                                 null,
    open_minute  int                                 null,
    close_hour   int                                 null,
    close_minute int                                 null,
    create_at    timestamp default CURRENT_TIMESTAMP null,
    update_at    timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
);

create table if not exists shop
(
    shop_id         varchar(64)                              not null
        primary key,
    user_id         varchar(64)                              null,
    school_id       varchar(64)                              null,
    area_id         varchar(64)                              null,
    floor           int                                      null,
    stall           int                                      null,
    name            varchar(64)                              null,
    avatar_url      varchar(255)                             null,
    background_url  varchar(255)                             null,
    goods_num       int            default 0                 null,
    notice          varchar(255)                             null,
    sign            varchar(64)                              null,
    phone           varchar(18)                              null,
    least_price     decimal(10, 2)                           null,
    delivery_price  decimal(10, 2)                           null,
    score           double(4, 2)   default 0.00              null,
    orders_all      int            default 0                 null,
    orders_month    int            default 0                 null,
    orders_week     int            default 0                 null,
    orders_day      int            default 0                 null,
    income_all      decimal(10, 2) default 0.00              null,
    income_month    decimal(10, 2) default 0.00              null,
    income_week     decimal(10, 2) default 0.00              null,
    income_day      decimal(10, 2) default 0.00              null,
    account_balance decimal(10, 2) default 0.00              null,
    predestine      bit            default b'0'              null,
    pick            bit            default b'0'              null,
    delivery        bit            default b'0'              null,
    `rank`          int            default 0                 null,
    open            bit            default b'0'              null,
    valid           bit            default b'0'              null,
    create_at       timestamp      default CURRENT_TIMESTAMP null,
    update_at       timestamp      default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
);

create table if not exists shop_category
(
    category_id varchar(64)                         not null
        primary key,
    school_id   varchar(64)                         null,
    name        varchar(64)                         null,
    shop_num    int                                 null,
    create_at   timestamp default CURRENT_TIMESTAMP null,
    update_at   timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    create_by   varchar(64)                         null,
    update_by   varchar(64)                         null
);

create table if not exists shop_menu
(
    menu_id   varchar(64)                         not null
        primary key,
    shop_id   varchar(64)                         null,
    name      varchar(64)                         null,
    goods_num int       default 0                 null,
    sort      int                                 null,
    create_at timestamp default CURRENT_TIMESTAMP null,
    update_at timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
);

create table if not exists shop_to_category
(
    id          varchar(64)                         not null
        primary key,
    shop_id     varchar(64)                         null,
    category_id varchar(64)                         null,
    create_at   timestamp default CURRENT_TIMESTAMP null
);

create table if not exists user
(
    user_id     varchar(64)                         not null comment '用户唯一表示码'
        primary key,
    nickname    varchar(64)                         null comment '昵称',
    avatar_url  varchar(255)                        null comment '用户头像',
    description varchar(256)                        null,
    username    varchar(64)                         null comment '用户账号（小程序用户设为空）',
    password    varchar(64)                         null comment '用户密码（小程序用户为空）',
    salt        varchar(64)                         null comment '加密盐值',
    open_id     varchar(64)                         null,
    union_id    varchar(64)                         null,
    client_id   varchar(64)                         null,
    school_id   varchar(64)                         null,
    phone       varchar(18)                         null,
    gender      int       default 0                 null,
    country     varchar(64)                         null,
    province    varchar(64)                         null,
    city        varchar(64)                         null,
    language    varchar(64)                         null,
    status      int                                 null comment '-1-注销、0-正常、1-审核、2-冻结',
    type        int                                 null comment '0-用户、1-商家、2-配送员',
    create_at   timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    update_at   timestamp                           null comment '更新时间',
    login_at    timestamp                           null comment '登录时间'
);

create table if not exists user_to_role
(
    id        varchar(64)                         not null
        primary key,
    user_id   varchar(64)                         null,
    role_id   varchar(64)                         null,
    create_at timestamp default CURRENT_TIMESTAMP null
);


