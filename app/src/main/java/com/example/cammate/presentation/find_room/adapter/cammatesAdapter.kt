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
    RecyclerView.Adapter<CammatesAdapter.CammateViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CammateViewHolder {
        val itemBinding: ItemCammateBinding =
            ItemCammateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CammateViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CammateViewHolder, position: Int) {
        holder.bind(cammates[position], selectedPosition == position)
    }

    override fun getItemCount(): Int {
        return cammates.size
    }

    fun getSelectedItem() = if (selectedPosition != RecyclerView.NO_POSITION) cammates[selectedPosition] else null
    fun setData(data: List<CammatesItem>) {
        cammates = data
        notifyDataSetChanged()
    }

    inner class CammateViewHolder(private val binding: ItemCammateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                notifyItemChanged(selectedPosition)
                selectedPosition = adapterPosition
                notifyItemChanged(selectedPosition)
            }
        }

        fun bind(cammate: CammatesItem, isSelected: Boolean) {
            binding.roomName.text = cammate.roomName
            itemView.isSelected = isSelected
        }
    }
}

