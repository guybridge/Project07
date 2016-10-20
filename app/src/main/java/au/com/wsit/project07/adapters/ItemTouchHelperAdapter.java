package au.com.wsit.project07.adapters;

/**
 * Created by guyb on 20/10/16.
 */
public interface ItemTouchHelperAdapter
{
    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
