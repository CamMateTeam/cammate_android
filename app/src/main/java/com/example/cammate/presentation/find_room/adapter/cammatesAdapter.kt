package com.example.cammate.presentation.viewer.find_room.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cammate.databinding.ItemCammateBinding

data class CammatesItem(
    val roomName: String,
)

class CammatesAdapter (
    private var cammates : List<CammatesItem>,
):
    RecyclerView.Adapter<CammateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CammateViewHolder {
        val itemBinding: ItemCammateBinding =
            ItemCammateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CammateViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CammateViewHolder, position: Int) {
        holder.bind(cammates[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return cammates.size
    }

    fun setData(data: List<CammatesItem>) {
        cammates = data
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    // (3) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    // (4) setItemClickListener로 설정한 함수 실행
    private lateinit var itemClickListener : OnItemClickListener

}

class CammateViewHolder(private val binding: ItemCammateBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(cammate: CammatesItem) {
        binding.roomName.text = cammate.roomName
    }
}



