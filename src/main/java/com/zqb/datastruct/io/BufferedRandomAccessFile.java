package com.zqb.datastruct.io;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
/**
 * RandomAccessFile每读/写一个字节就需对磁盘进行一次I/O操作。所以在RandomAccessFile的基础上可以加上缓存效率上是非常的慢的（大概慢100倍）<br/>
 * 本来是想写一个带缓存的RandomAccessFile的类，但，发现JDK的RadomAccessFile的read方法是有带buff的，效率还可以（10M,10ms），但在readLine的时候，就不太理想了
 * @author zhengqb
 *         created 2014年1月9日
 */
public class BufferedRandomAccessFile extends RandomAccessFile {

	private final static int DEFLAUTBUFFERSIZE = 2048;
	
	protected byte buf[]; // 建立读缓存区

	private long bufstartpos;

	private long bufendpos;

	private long curpos;

	private boolean bufdirty;

	private int bufusedsize;

	private long fileendpos;

	private long bufbitlen;

	private long bufsize;
	
	public BufferedRandomAccessFile(String name, String mode) throws FileNotFoundException {
		super(name, mode);
	}
	
	public byte read(long pos) throws IOException {
        if (pos < this.bufstartpos || pos > this.bufendpos ) {
            this.flushbuf();
            this.seek(pos);
            if ((pos < this.bufstartpos) || (pos > this.bufendpos)) 
                throw new IOException();
        }
        this.curpos = pos;
        return this.buf[(int)(pos - this.bufstartpos)];
    }

	/**
	 * bufdirty为真，把buf[]中尚未写入磁盘的数据，写入磁盘。
	 * @throws IOException 
	 */
	private void flushbuf() throws IOException {
		if (this.bufdirty == true) {
            if (super.getFilePointer() != this.bufstartpos) {
                super.seek(this.bufstartpos);
            }
            super.write(this.buf, 0, this.bufusedsize);
            this.bufdirty = false;
        }
	}

	public void seek(long pos) throws IOException {
        if ((pos < this.bufstartpos) || (pos > this.bufendpos)) { // seek pos not in buf
            this.flushbuf();
            if ((pos >= 0) && (pos <= this.fileendpos) && (this.fileendpos != 0)) {   
            	// seek pos in file (file length > 0)
            	  this.bufstartpos =  pos * bufbitlen / bufbitlen;
                this.bufusedsize = this.fillbuf();
            } else if (((pos == 0) && (this.fileendpos == 0)) || (pos == this.fileendpos + 1)) {   
            	// seek pos is append pos
                this.bufstartpos = pos;
                this.bufusedsize = 0;
            }
            this.bufendpos = this.bufstartpos + this.bufsize - 1;
        }
        this.curpos = pos;
    }

	/**
	 * 根据bufstartpos，填充buf[]。
	 * @return
	 * @throws IOException
	 */
	private int fillbuf() throws IOException {
		super.seek(this.bufstartpos);
        this.bufdirty = false;
        return super.read(this.buf);
	}
	
	
	
	public static void main(String[] args) throws IOException {
		//30922 21462 18975
		
		/*RandomAccessFile raf = new RandomAccessFile("D:/zqb_source.log", "r");
		
		long startTimme = System.currentTimeMillis();
		byte buf[] = new byte[1024];
    	int readcount;
		String line = null;
		while((readcount=raf.read(buf))>0) {
		}
		System.err.println(System.currentTimeMillis()-startTimme);*/
		
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream("D:/zqb_source.log"));
		long startTimme = System.currentTimeMillis();
		
		byte[] buff = new byte[2048];
		
		int len = 0;
		while((len=bis.read(buff))>0) {
			//System.out.println(new String(buff, 0, len));
		}
		System.err.println(System.currentTimeMillis()-startTimme);
	}
}
