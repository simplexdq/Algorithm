public class Solution {
  private static final char[] PS = new char[] { '(', ')', '<', '>', '{', '}'};
  public List<String> validParentheses(int l, int m, int n) {
    //remain记录每类括号的数量，与PS对应
    int[] remain = new int[] { l, l, m, m, n, n };
    int targetLen = 2 * l + 2 * m + 2 * n;
    StringBuilder cur = new StringBuilder();
    //stack用来记录右括号与左括号的配对情况
    Deque<Integer> stack = new LinkedList<Integer>();
    List<String> result = new ArrayList<String>();
    helper(cur, stack, remain, targetLen, result);
    return result;
  }

  private void helper(StringBuilder cur, Deque<Integer> stack, int[] remain,
  int targetLen, List<String> result) {
    //base case
    if(cur.length() == targetLen) {
      result.add(cur.toString());
      return;
    }
    for(int i = 0; i < remain.length; i++) {
      if(i % 2 == 0) { //left
        if(remain[i] > 0) {
          //只要还有左括号，就向cur和stack里add,同时将remain里的左括号数-1（计数）
          cur.append(PS[i]);
          stack.offerFirst(i);
          remain[i]--;
          //可以理解为触底的过程
          helper(cur, stack, remain, targetLen, result); 
          //从right 触底反弹之后，left重新找其他可能，步骤同于触底
          cur.deleteCharAt(cur.length() - 1);
          stack.pollFirst();
          remain[i]++;
        }
      } else { //right
      //如果stcak is empty, 则表示没有左括号了，不考虑加任何右括号
        if(!stack.isEmpty() && stack.peekFirst() == i - 1) {
          //每加一个右括号，就将其对应的左括号pop掉，并且，remain里的右括号数-1（计数）
          cur.append(PS[i]);
          stack.pollFirst();
          remain[i]--;
          //先和left一起触底；触底之后，return回到此步，从下一步接着走
          helper(cur, stack, remain, targetLen, result);
          //当触底之后，stack is empty, cur.length == targetLen, so
          //return 到此位置后，要删cur, 加stack, 也就是一步步向上反弹
          cur.deleteCharAt(cur.length() - 1);
          stack.offerFirst(i - 1);
          remain[i]++;
          //进行下一步for循环，即i++；如果i>remain.length,break;
          //回到break时的resursive step,从那一步再向下走
          //【break 发生在：left和right 中调用helper（）的位置】
        }
      }
    }
  }
}
