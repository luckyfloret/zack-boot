package cn.hmg.zackblog.module.system.mq.topic.menu;

/**
 * @author hmg
 * @version 1.0
 * @date 2023-07-28 17:36
 * @description: 菜单topic
 */
public class MenuTopic {
    public static final String MENU_REFRESH_CACHE = "system_menu_refresh_cache";
    public static final String CONSUMER_GROUP = "menu_refresh_consumer_group";

    /**
     * tag以业务模块区分
     */
    public static final String TAG = "system_menu";
}
