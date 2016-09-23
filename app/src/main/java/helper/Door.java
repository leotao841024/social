package helper;

import com.hurray.plugins.rkctrl;

/**
 * Created by jam on 2016/8/16.
 */
public class Door {

    public static  rkctrl  m_rkctrl = new rkctrl();
    public static void open(){
        m_rkctrl.exec_io_cmd(4, 1);
        m_rkctrl.exec_io_cmd(6, 1);
    }

    public static void close(){
        m_rkctrl.exec_io_cmd(4, 0);
        m_rkctrl.exec_io_cmd(6, 0);
    }
    public static int get_adc_status(int i){
        return m_rkctrl.get_adc_status(i);
    }
}
