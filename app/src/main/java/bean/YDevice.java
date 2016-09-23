package bean;

/**
 * Created by Jam on 2016/8/7.
 */
public class YDevice {
    static {
        try {
            System.loadLibrary("building");
            System.loadLibrary("serial_port");
        } catch (UnsatisfiedLinkError ule) {
        }
    }
    // 检测锁状态
    public static native int openled();
    // 关闭锁
    public static native void closeled();
    // 开普通门锁
    public static native int ioctl(int led_num, int on_off);
    // 开继电器门锁
    public static native int ioct2(int led_num, int on_off);
    // 获取当前是白天还是黑夜
    public static native int getLightIntensityStatus();
    // 摄像头灯光的打开与关闭
    public static native int controlCamera(int led_num, int on_off);
}
