package cn.loverot.basic.utils;


import com.sun.jna.Library;
import com.sun.jna.Native;

public interface Kernel32  extends Library {
    public static Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("kernel32", Kernel32.class);

    /**
     * 获取进程id
     * @param hProcess
     * @return
     */
    public long GetProcessId(Long hProcess);

    /**
     * 获取进程退出代码
     * @param hProcess
     * @param i
     * @return
     */
    public boolean GetExitCodeProcess(Long hProcess,Integer i);

    /**
     * @param hand
     * @param in
     * @param pid
     * @return
     */
    public long OpenProcess(Long hand,Boolean in,Long pid);



}
