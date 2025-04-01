package com.piedaholic.algo.leetcode;

/**
 * The count-and-say sequence is a sequence of digit strings defined by the recursive formula:
 *     <p>countAndSay(1) = "1"</p>
 *     <p>countAndSay(n) is the run-length encoding of countAndSay(n - 1).</p>
 */
public class CountAndSay {
    public static void main(String[] args){
        CountAndSay cs = new CountAndSay();
        System.out.println(cs.rle(3));
    }

    /**
     * <a href="https://en.wikipedia.org/wiki/Run-length_encoding">Run-length encoding (RLE)</a> is a string compression method that works by replacing
     * consecutive identical characters (repeated 2 or more times) with the concatenation
     * of the character and the number marking the count of the characters (length of the run).
     * <p>For example, to compress the string "3322251" we replace "33" with "23", replace "222" with "32",
     * replace "5" with "15" and replace "1" with "11".
     * Thus, the compressed string becomes "23321511".</p>
     */
    private String rle(int n) {
        if (n == 1) {
            return "1";
        }
        String last = rle(n-1);
        return compute(last);
    }

    private String compute(String s) {
        StringBuilder sb = new StringBuilder();
        char c = s.charAt(0);
        int count = 1;
        for(int i = 1; i < s.length(); i++){
            if(s.charAt(i) == c){
                count++;
            }
            else
            {
                sb.append(count);
                sb.append(c);
                c = s.charAt(i);
                count = 1;
            }
        }
        sb.append(count);
        sb.append(c);
        return sb.toString();
    }
}
