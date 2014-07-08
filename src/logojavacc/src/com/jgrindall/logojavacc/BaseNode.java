/**
 * BaseNode.java
 * @author jgrindall
 * Created
 * Last modified
 * **********************************
 * Base class given to JavaCC to extend for all nodes
 * contains a data field for things such as :x=2.
 * The variable name 'x' and the number '2' need
 * to be stored in the nodes.
 */
package com.jgrindall.logojavacc;
import java.util.HashMap;
public class BaseNode{
    protected HashMap data=new HashMap();
    public int uniqueid=-1;
    public static int last=0;
    public BaseNode(){
        uniqueid= BaseNode.last;
        BaseNode.last++;
        // uniqueid and last are useful for debugging
    }
    /**
     * 
     * @param key
     * @param v
     */
    public void addToData(String key, Object v){
        data.put(key, v);
    }
    /**
     *
     * @return
     */
    public HashMap getHashMap(){
        return data;
    }
    /**
     * 
     * @param k
     * @return
     */
    public Object getData(String k){
        return data.get(k);
    }
}
