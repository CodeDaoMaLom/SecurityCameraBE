package tech.thanhpham.homemanagementbe.Security;

public class securityConstants {
    public static String JWT_SECRET = "Thanhtpham";
    public static final long JWT_EXP = 365L * 24 * 1000 * 3600;

    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
}
