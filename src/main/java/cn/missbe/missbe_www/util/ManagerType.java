package cn.missbe.missbe_www.util;

/**
 * Created by Administrator on 2016/11/11 0011.
 */
public enum ManagerType {
    supervisor, general;
    public static ManagerType strToTypel(String managerType) {
        if (StringUtils.isBlank(managerType)) {
            return general;
        }
        switch (managerType) {
            case "超级管理员":
                return supervisor;
            case "普通管理员":
                return general;
            default:
                return general;
        }
    }
    public static String typeToStr(ManagerType managerType) {

        switch (managerType) {
            case supervisor:
                return "超级管理员";
            case general:
                return "普通管理员";
            default:
                return "普通管理员";
        }
    }
    public static int typeToInt(ManagerType managerType) {

        switch (managerType) {
            case supervisor:
                return 0;
            case general:
                return 1;
            default:
                return 1;
        }
    }
}
