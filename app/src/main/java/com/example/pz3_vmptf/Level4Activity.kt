package com.example.pz3_vmptf

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Level4Activity : AppCompatActivity() {

    private lateinit var recipeNameEditText: EditText
    private lateinit var ingredientsEditText: EditText
    private lateinit var instructionsEditText: EditText
    private lateinit var addRecipeButton: Button
    private lateinit var recipesInfoTextView: TextView
    private lateinit var recipesListView: ListView

    private val recipes = mutableListOf<Recipe>()
    private val recipeTitles = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_level4)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.level4Main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recipeNameEditText = findViewById(R.id.recipeNameEditText)
        ingredientsEditText = findViewById(R.id.ingredientsEditText)
        instructionsEditText = findViewById(R.id.instructionsEditText)
        addRecipeButton = findViewById(R.id.addRecipeButton)
        recipesInfoTextView = findViewById(R.id.recipesInfoTextView)
        recipesListView = findViewById(R.id.recipesListView)

        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            recipeTitles
        )

        recipesListView.adapter = adapter

        addRecipeButton.setOnClickListener {
            addRecipe()
        }

        recipesListView.setOnItemClickListener { _, _, position, _ ->
            shareRecipe(recipes[position])
        }

        updateRecipesInfo()
    }

    private fun addRecipe() {
        val name = recipeNameEditText.text.toString().trim()
        val ingredients = ingredientsEditText.text.toString().trim()
        val instructions = instructionsEditText.text.toString().trim()

        if (name.isEmpty() || ingredients.isEmpty() || instructions.isEmpty()) {
            Toast.makeText(this, "Заповніть усі поля рецепта", Toast.LENGTH_SHORT).show()
            return
        }

        val recipe = Recipe(
            name = name,
            ingredients = ingredients,
            instructions = instructions
        )

        recipes.add(recipe)
        recipeTitles.add(recipe.name)

        adapter.notifyDataSetChanged()

        recipeNameEditText.text.clear()
        ingredientsEditText.text.clear()
        instructionsEditText.text.clear()

        updateRecipesInfo()

        Toast.makeText(this, "Рецепт додано", Toast.LENGTH_SHORT).show()
    }

    private fun shareRecipe(recipe: Recipe) {
        val recipeText = """
            Рецепт: ${recipe.name}
            
            Інгредієнти:
            ${recipe.ingredients}
            
            Інструкції приготування:
            ${recipe.instructions}
        """.trimIndent()

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, recipeText)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "Поділитися рецептом")
        startActivity(shareIntent)
    }

    private fun updateRecipesInfo() {
        if (recipes.isEmpty()) {
            recipesInfoTextView.text = "Список рецептів порожній"
        } else {
            recipesInfoTextView.text = "Кількість рецептів: ${recipes.size}. Натисніть на рецепт, щоб переглянути і поділитися."
        }
    }
}