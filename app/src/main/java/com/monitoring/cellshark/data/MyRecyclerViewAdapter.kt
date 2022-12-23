package com.monitoring.cellshark.data

import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.monitoring.cellshark.R


internal class MyRecyclerViewAdapter(private var itemsList: MutableList<Endpoint>): RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            return if (viewType == Constants.PARENT) {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.parent_row, parent, false)
                ParentViewHolder(itemView)

            } else {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.child_row, parent, false)
                ChildViewHolder(itemView)
            }
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val dataList = itemsList[position]
            Log.d("RVV", "Endpoint Selected: ${dataList.endpointName}")
            if (dataList.type == Constants.PARENT) {

                holder as ParentViewHolder
                holder.apply {
                    parentTV?.text = dataList.endpointName
                    if (dataList.parentResult == null) resultImage?.setBackgroundResource(R.drawable.ic_baseline_question_mark_24)
                    else if (dataList.parentResult!!) resultImage?.setBackgroundResource(R.drawable.ic_baseline_check_circle_outline_24)
                    else resultImage?.setBackgroundResource(R.drawable.ic_baseline_block_24)
                    downIVLayout?.setOnClickListener { expandOrCollapseParentItem(dataList, position) }
                }
            } else {
                holder as ChildViewHolder
                holder.apply {

                    val endpointResult = dataList.result.first()
                    Log.d("RVValue", "POSITION: $position address: ${endpointResult.address}, result: ${endpointResult.result}")
                    childTV?.text = endpointResult.address

                    if (!endpointResult.result) {
                        childTV?.setTextColor(Color.RED)
                    } else {
                        childTV?.setTextColor(Color.GRAY)
                    }
                }

            }

        }

        override fun getItemCount(): Int {
            return itemsList.size
        }

    override fun getItemViewType(position: Int): Int = itemsList[position].type
    override fun getItemId(position: Int): Long { return position.toLong() }
    private fun expandOrCollapseParentItem(singleBoarding: Endpoint,position: Int) {
        if (singleBoarding.isExpanded) { collapseParentRow(position) }
        else { expandParentRow(position) }
    }
    private fun expandParentRow(position: Int){
        val currentBoardingRow = itemsList[position]
        val services = currentBoardingRow.result
        currentBoardingRow.isExpanded = true
        var nextPosition = position
        if(currentBoardingRow.type==Constants.PARENT){

            services.forEach { service ->
                val parentModel =  Endpoint(itemsList[position].endpoints, itemsList[position].endpointName, itemsList[position].epType, itemsList[position].description)
                parentModel.type = Constants.CHILD
                val subList : ArrayList<EndpointResult> = ArrayList()
                subList.add(service)
                parentModel.result=subList
                itemsList.add(++nextPosition,parentModel)
            }
            notifyDataSetChanged()
        }
    }
    private fun collapseParentRow(position: Int){
        val currentEndpointRow = itemsList[position]
        val services = currentEndpointRow.result
        itemsList[position].isExpanded = false
        if(itemsList[position].type==Constants.PARENT){
            services.forEach { _ ->
                itemsList.removeAt(position + 1)
            }
            notifyDataSetChanged()
        }
    }


    class ParentViewHolder(row: View): ViewHolder(row) {

        val parentTV: TextView? = row.findViewById(R.id.parentTitle)
        val resultImage: ImageView? = row.findViewById(R.id.statusDynamic)
        val downIVLayout: LinearLayout? = row.findViewById(R.id.down_iv_layout)

    }

    class ChildViewHolder(row: View): ViewHolder(row) {
        val childTV: TextView? = row.findViewById(R.id.childTitle)
    }
}
