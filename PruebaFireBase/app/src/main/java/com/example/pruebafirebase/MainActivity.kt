package com.example.pruebafirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

data class ClasePrueba(val ID:String?,val texto:String, val Vector:ArrayList<Any>)

class MainActivity : AppCompatActivity() {
    val db=FirebaseFirestore.getInstance()
    val ref = FirebaseFirestore.getInstance().document("ColeccionEjemplo/docEjemplo")
    val ref2 = FirebaseFirestore.getInstance().collection("ColeccionEjemplo").document("docEjemplo2")

    override fun onCreate(savedInstanceState: Bundle?) {
        var i=0
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val TAG="escrito cualquiera"

//        db.collection("ColeccionEjemplo")
//            .whereEqualTo("camp", "23")
//            .addSnapshotListener { snapshots, e ->
//                if (e != null) {
//                    Log.w(TAG, "listen:error", e)
//                    return@addSnapshotListener
//                }
//
//                for (dc in snapshots!!.documentChanges) {
//                    when (dc.type) {
//                        DocumentChange.Type.ADDED -> Log.e(TAG, "New city: ${dc.document}")
//                        DocumentChange.Type.MODIFIED -> Log.e(TAG, "Modified city: ${dc.document.data}")
//                        DocumentChange.Type.REMOVED -> Log.e(TAG, "Removed city: ${dc.document.data}")
//                    }
//                }
//            }
//
//        ref.addSnapshotListener{snapshot,e ->
//            val a = snapshot!!.get("vector") as ArrayList<Any>
//
//            Log.e("asdas",a.get(2)::class.toString())
//
//        }

/////////////////////Cada vez que se se detecte un cambio en el docuemnto se ejecuta
//        ref.addSnapshotListener { snapshot, e ->
//            if (snapshot!!.exists()){
//                val a=snapshot.getString("id")
//                Texto.text="El ID del documento que se cambio es: $a"
//                Toast.makeText(applicationContext,"pruebas",Toast.LENGTH_SHORT).show()
//            }
//        }

        guardar_dato.setOnClickListener{
            val b=ArrayList<Any>()
            b.add("texto de array")
            b.add(23)
            ref.set(ClasePrueba("texto ID $i","texto random",b))
            //db.document("ColeccionEjemplo/Ej1").set(ClasePrueba("hola $i","como estas",b))
            i++
        }

//        guardar_dato.setOnClickListener{
//            val b=ArrayList<Any>()
//            b.add("asdas")
//            b.add(23)
//            ref2.set(ClasePrueba("co","como estas",b))
//        }

        //////////////Trae el dato "id" del documento especificado por ref cuando oprime el boton traer dato
        traer_dato.setOnClickListener{
            ref.get().addOnSuccessListener{
                if(it.exists()){
                    val id=it.getString("id")
                    val vector=it.get("vector") as ArrayList<Any>
                    Log.e("elementos del vector:",vector.toString())
                    Texto.text=id
                }
            }
        }
    }
}
