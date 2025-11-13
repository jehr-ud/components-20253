package com.ud.myapplication.ui.composables.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ud.myapplication.data.model.Card

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    val handCards = listOf(
        Card("Blue-Eyes White Dragon", "Dragon", "This legendary dragon is a powerful engine of destruction.", 3000, 2500, ""),
        Card("Dark Magician", "Spellcaster", "The ultimate wizard in terms of attack and defense.", 2500, 2100, ""),
        Card("Red-Eyes Black Dragon", "Dragon", "A ferocious dragon with a deadly attack.", 2400, 2000, ""),
        Card("Kuriboh", "Fiend", "A furry creature that multiplies when attacked.", 300, 200, ""),
        Card("Gaia The Fierce Knight", "Warrior", "A knight who charges at his enemies with his lances.", 2300, 2100, "")
    )

    val deckCards = listOf(
        Card("Summoned Skull", "Fiend", "A fiend with dark powers for confusing the enemy.", 2500, 1200, ""),
        Card("Celtic Guardian", "Warrior", "An elf who is a master swordsman.", 1400, 1200, ""),
        Card("Giant Soldier of Stone", "Rock", "A giant warrior made of stone.", 1300, 2000, ""),
        Card("Man-Eater Bug", "Insect", "Gains 500 ATK for each Insect monster on the field.", 450, 600, ""),
        Card("Harpie Lady", "Winged Beast", "A beautiful but deadly winged creature.", 1300, 1400, ""),
        Card("Mystical Elf", "Spellcaster", "A delicate elf that lacks offense, but has a terrific defense.", 800, 2000, "")
    )

    Column(modifier = modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text("Your Hand", style = MaterialTheme.typography.headlineSmall)
        LazyRow(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(handCards) { card ->
                CardItem(card = card, modifier = Modifier.width(180.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Exchangeable Cards (Deck)", style = MaterialTheme.typography.headlineSmall)
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 120.dp),
            modifier = Modifier.padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(deckCards) { card ->
                CardItem(card = card)
            }
        }
    }
}

@Composable
fun CardItem(card: Card, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .background(Color.DarkGray)
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = card.name, style = MaterialTheme.typography.titleSmall, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(text = card.type, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "ATK/${card.attack}", style = MaterialTheme.typography.labelMedium)
                    Text(text = "DEF/${card.defense}", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}
