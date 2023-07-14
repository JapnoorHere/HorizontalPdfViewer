package com.droidbytes.horizontalpdfviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.droidbytes.horizontalpdfviewer.databinding.ActivityMainBinding
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener
import com.shockwave.pdfium.PdfDocument.Bookmark

class MainActivity : AppCompatActivity(), OnPageChangeListener, OnLoadCompleteListener {
    lateinit var binding : ActivityMainBinding
    var pageNumber : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pdfView.fromAsset("sample.pdf").defaultPage(pageNumber)
            .enableSwipe(true)
            .swipeHorizontal(true)
            .onPageChange(this@MainActivity)
            .enableAnnotationRendering(true)
            .onLoad(this)
            .load()

    }

    override fun onPageChanged(page: Int, pageCount: Int) {
        pageNumber = page
        binding.page.setText(String.format("%s %s / %s","PDF",page+1,pageCount))
    }

    override fun loadComplete(nbPages: Int) {
//        val meta = binding.pdfView.documentMeta
        printBookMarkTree(binding.pdfView.tableOfContents,"-")
    }

    fun printBookMarkTree(tree : List<Bookmark> , sep : String){
        for(b in tree){
            if(b.hasChildren()){
                printBookMarkTree(b.children,"$sep-")
            }
        }
    }
}