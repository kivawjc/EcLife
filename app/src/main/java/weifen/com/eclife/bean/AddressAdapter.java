package weifen.com.eclife.bean;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import weifen.com.eclife.R;

public class AddressAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<Address> list;
	public AddressAdapter(Context context, ArrayList<Address> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}
	@Override
	public Object getItem(int position) {
		return null;
	}
	@Override
	public long getItemId(int position) {
		return 0;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = View.inflate(context, R.layout.item_address_list,null);
		}
		
		ViewHolder holder = ViewHolder.getHolder(convertView);
		//设置数据
		Address friend = list.get(position);
		holder.name.setText(friend.getName());
		
		String currentWord = friend.getPinyin().charAt(0)+"";
		if(position>0){
			//获取上一个item的首字母
			String lastWord = list.get(position-1).getPinyin().charAt(0)+"";


			//拿当前的首字母和上一个首字母比较
			if(currentWord.equals(lastWord)){
				//说明首字母相同，需要隐藏当前item的first_word
				holder.first_word.setVisibility(View.INVISIBLE);//默认占空间
			}else {
				//不一样，需要显示当前的首字母
				//由于布局是复用的，所以在需要显示的时候，再次将first_word设置为可见
				holder.first_word.setVisibility(View.VISIBLE);
				holder.first_word.setText(currentWord);
			}


		}else {
			holder.first_word.setVisibility(View.VISIBLE);
			holder.first_word.setText(currentWord);
		}
		
		return convertView;
	}

	static class ViewHolder{
		TextView name,first_word;
		
		public ViewHolder(View convertView){
			name = (TextView) convertView.findViewById(R.id.name);
			first_word = (TextView) convertView.findViewById(R.id.first_word);
		}
		public static ViewHolder getHolder(View convertView){
			ViewHolder holder = (ViewHolder) convertView.getTag();//控件重用时,可以不用findViewbyId
			if(holder==null){
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			}
			return holder;
		}
	}
}
