package com.test.jvm.demo1;

/**
 * 修改Class文件，暂时只提供修改常量池常量的功能
 * @author zyl
 * **/
public class ClassModifier {
	/**
	 * Class文件中常量池的起始偏移
	 * */
	private static final int CONSTANT_POOL_INDEX=8;
	
	/**
	 *CONSTANT_Utf8_info 常量的tag标志
	 * */
	private static final int CONSTANT_Utf8_info = 1 ;
	
	/**
	 * 常量池中11种常量所占的长度，CONSTANT_Utf8_info 型常量除外，因为它不是定长
	 * */
	private static final int[] CONSTANT_ITEM_LENGTH = {-1,-1,-1,5,5,9,9,3,3,5,5,5,5};
	
	private static final int u1 = 1;
	private static final int u2 = 2 ;
	
	private byte[] classByte ;
	
	public ClassModifier(byte[] classByte){
		this.classByte = classByte ;
	}
	
	/***
	 * 修改常量池中CONSTANT_Utf8_info常量内容
	 * @param oldStr 修改前的字符串
	 * @param newStr 修改后的字符串
	 * @return 修改结果
	 * */
	public byte[] modifyUTF8Constant(String oldStr , String newStr){
		int cpc = getConstantPoolCount();
		int offset = CONSTANT_POOL_INDEX  + u2 ;
		for(int i=0 ; i<cpc ; i++){
			int tag = ByteUtils.byte2Int(classByte, offset, u1);
			if(tag == CONSTANT_Utf8_info){
				int len = ByteUtils.byte2Int(classByte, offset+u1, u2);
				offset += ( u1 + u2 );
				String str = ByteUtils.byte2String(classByte, offset, len);
				if(str.equalsIgnoreCase(oldStr)){
					byte[] strBytes = ByteUtils.string2Bytes(newStr);
					byte[] strLen = ByteUtils.int2Bytes(newStr.length(), u2);
					classByte = ByteUtils.byteReplace(classByte, offset - u2, u2 , strLen) ;
					classByte = ByteUtils.byteReplace(classByte, offset, len, strBytes);
					return classByte ;
					
				}else{
					offset += len ;
				}
			}else{
				offset += CONSTANT_ITEM_LENGTH[tag];
			}
		}
		
		return classByte;
		
		
	}
	
	public int getConstantPoolCount(){
		return ByteUtils.byte2Int(classByte, CONSTANT_POOL_INDEX, u2);
	}
	
	
}
