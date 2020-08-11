public class ListNode {
    int val;
    ListNode next;
    ListNode(int x){val = x;}
    public String toString(){
        String str = Integer.toString(val);
        ListNode temp = this.next;
        while(temp!=null){
            str = str + "->" + temp.val;
            temp = temp.next;
        }
        return str;
    }
}
