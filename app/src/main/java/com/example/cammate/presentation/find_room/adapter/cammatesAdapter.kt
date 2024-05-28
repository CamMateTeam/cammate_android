package com.example.cammate.presentation.viewer.find_room.adapter
import android.view.LayoutInflater
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
    }

    override fun getItemCount(): Int {
        return cammates.size
    }

    fun setData(data: List<CammatesItem>) {
        cammates = data
        notifyDataSetChanged()
    }
}

class CammateViewHolder(private val binding: ItemCammateBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(cammate: CammatesItem) {
        binding.roomName.text = cammate.roomName
    }
}

