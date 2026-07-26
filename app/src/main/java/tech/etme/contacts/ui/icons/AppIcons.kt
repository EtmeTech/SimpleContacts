package tech.etme.contacts.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private fun icon(name: String, build: androidx.compose.ui.graphics.vector.PathBuilder.() -> Unit): ImageVector =
    ImageVector.Builder(
        name = name, defaultWidth = 24.dp, defaultHeight = 24.dp,
        viewportWidth = 24f, viewportHeight = 24f
    ).path(fill = SolidColor(Color.White), pathBuilder = build).build()

object AppIcons { //ai generated Icons
    val Add: ImageVector by lazy {
        icon("Add") {
            moveTo(11f, 5f); lineTo(13f, 5f); lineTo(13f, 11f)
            lineTo(19f, 11f); lineTo(19f, 13f); lineTo(13f, 13f)
            lineTo(13f, 19f); lineTo(11f, 19f); lineTo(11f, 13f)
            lineTo(5f, 13f); lineTo(5f, 11f); lineTo(11f, 11f)
            close()
        }
    }

    val RemoveCircle: ImageVector by lazy {
        icon("RemoveCircle") {
            moveTo(12f, 2f)
            curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f)
            curveTo(2f, 17.52f, 6.48f, 22f, 12f, 22f)
            curveTo(17.52f, 22f, 22f, 17.52f, 22f, 12f)
            curveTo(22f, 6.48f, 17.52f, 2f, 12f, 2f)
            close()
            moveTo(17f, 13f); lineTo(7f, 13f); lineTo(7f, 11f); lineTo(17f, 11f)
            close()
        }
    }

    val Person: ImageVector by lazy {
        icon("Person") {
            moveTo(12f, 12f)
            curveTo(14.21f, 12f, 16f, 10.21f, 16f, 8f)
            curveTo(16f, 5.79f, 14.21f, 4f, 12f, 4f)
            curveTo(9.79f, 4f, 8f, 5.79f, 8f, 8f)
            curveTo(8f, 10.21f, 9.79f, 12f, 12f, 12f)
            close()
            moveTo(12f, 14f)
            curveTo(9.33f, 14f, 4f, 15.34f, 4f, 18f)
            lineTo(4f, 20f); lineTo(20f, 20f); lineTo(20f, 18f)
            curveTo(20f, 15.34f, 14.67f, 14f, 12f, 14f)
            close()
        }
    }

    val Back: ImageVector by lazy {
        icon("Back") {
            moveTo(20f, 11f); lineTo(7.83f, 11f)
            lineTo(13.42f, 5.41f); lineTo(12f, 4f)
            lineTo(4f, 12f); lineTo(12f, 20f)
            lineTo(13.42f, 18.59f); lineTo(7.83f, 13f)
            lineTo(20f, 13f)
            close()
        }
    }

    val Search: ImageVector by lazy {
        icon("Search") {
            moveTo(15.5f, 14f)
            lineTo(20.5f, 19f)
            lineTo(19f, 20.5f)
            lineTo(14f, 15.5f)
            close()
            moveTo(9.5f, 4f)
            curveTo(13.09f, 4f, 16f, 6.91f, 16f, 10.5f)
            curveTo(16f, 14.09f, 13.09f, 17f, 9.5f, 17f)
            curveTo(5.91f, 17f, 3f, 14.09f, 3f, 10.5f)
            curveTo(3f, 6.91f, 5.91f, 4f, 9.5f, 4f)
            close()
            moveTo(9.5f, 6f)
            curveTo(7.01f, 6f, 5f, 8.01f, 5f, 10.5f)
            curveTo(5f, 12.99f, 7.01f, 15f, 9.5f, 15f)
            curveTo(11.99f, 15f, 14f, 12.99f, 14f, 10.5f)
            curveTo(14f, 8.01f, 11.99f, 6f, 9.5f, 6f)
            close()
        }
    }

    val StarOutline: ImageVector by lazy {
        icon("StarOutline") {
            moveTo(12f, 17.27f)
            lineTo(18.18f, 21f)
            lineTo(16.54f, 13.97f)
            lineTo(22f, 9.24f)
            lineTo(14.81f, 8.63f)
            lineTo(12f, 2f)
            lineTo(9.19f, 8.63f)
            lineTo(2f, 9.24f)
            lineTo(7.46f, 13.97f)
            lineTo(5.82f, 21f)
            close()
            moveTo(12f, 15.4f)
            lineTo(8.24f, 17.67f)
            lineTo(9.24f, 13.39f)
            lineTo(5.92f, 10.51f)
            lineTo(10.3f, 10.13f)
            lineTo(12f, 6.1f)
            lineTo(13.71f, 10.13f)
            lineTo(18.09f, 10.51f)
            lineTo(14.77f, 13.39f)
            lineTo(15.77f, 17.67f)
            close()
        }
    }

    val StarFilled: ImageVector by lazy {
        icon("StarFilled") {
            moveTo(12f, 17.27f)
            lineTo(18.18f, 21f)
            lineTo(16.54f, 13.97f)
            lineTo(22f, 9.24f)
            lineTo(14.81f, 8.63f)
            lineTo(12f, 2f)
            lineTo(9.19f, 8.63f)
            lineTo(2f, 9.24f)
            lineTo(7.46f, 13.97f)
            lineTo(5.82f, 21f)
            close()
        }
    }

    val Heart: ImageVector by lazy {
        icon("Heart") {
            moveTo(12f, 21f)
            curveTo(11.6f, 21f, 11.2f, 20.86f, 10.88f, 20.58f)
            curveTo(7.34f, 17.44f, 4f, 14.13f, 4f, 10.5f)
            curveTo(4f, 7.83f, 6.09f, 5.75f, 8.75f, 5.75f)
            curveTo(10.09f, 5.75f, 11.32f, 6.32f, 12f, 7.28f)
            curveTo(12.68f, 6.32f, 13.91f, 5.75f, 15.25f, 5.75f)
            curveTo(17.91f, 5.75f, 20f, 7.83f, 20f, 10.5f)
            curveTo(20f, 14.13f, 16.66f, 17.44f, 13.12f, 20.58f)
            curveTo(12.8f, 20.86f, 12.4f, 21f, 12f, 21f)
            close()
        }
    }

    val Wrench: ImageVector by lazy {
        icon("Wrench") {
            moveTo(21.71f, 5.29f)
            curveTo(21.32f, 4.9f, 20.68f, 4.9f, 20.29f, 5.29f)
            lineTo(16.4f, 9.18f)
            lineTo(14.82f, 7.6f)
            lineTo(18.71f, 3.71f)
            curveTo(18.32f, 3.32f, 17.68f, 3.32f, 17.29f, 3.71f)
            curveTo(15.42f, 2.6f, 12.98f, 2.83f, 11.34f, 4.46f)
            curveTo(9.79f, 6.02f, 9.5f, 8.31f, 10.45f, 10.16f)
            lineTo(2.29f, 18.32f)
            curveTo(1.9f, 18.71f, 1.9f, 19.34f, 2.29f, 19.73f)
            lineTo(4.29f, 21.73f)
            curveTo(4.68f, 22.12f, 5.31f, 22.12f, 5.7f, 21.73f)
            lineTo(13.86f, 13.57f)
            curveTo(15.71f, 14.52f, 18f, 14.23f, 19.56f, 12.68f)
            curveTo(21.19f, 11.04f, 21.42f, 8.6f, 20.31f, 6.73f)
            close()
        }
    }

    val Envelope: ImageVector by lazy {
        icon("Envelope") {
            moveTo(4f, 5f)
            lineTo(20f, 5f)
            curveTo(21.1f, 5f, 22f, 5.9f, 22f, 7f)
            lineTo(22f, 17f)
            curveTo(22f, 18.1f, 21.1f, 19f, 20f, 19f)
            lineTo(4f, 19f)
            curveTo(2.9f, 19f, 2f, 18.1f, 2f, 17f)
            lineTo(2f, 7f)
            curveTo(2f, 5.9f, 2.9f, 5f, 4f, 5f)
            close()
            moveTo(20f, 7f)
            lineTo(12f, 12f)
            lineTo(4f, 7f)
            lineTo(4f, 7f)
            lineTo(4f, 9f)
            lineTo(12f, 14f)
            lineTo(20f, 9f)
            lineTo(20f, 7f)
            close()
        }
    }

    val Phone: ImageVector by lazy {
        icon("Phone") {
            moveTo(6.62f, 10.79f)
            curveTo(8.06f, 13.62f, 10.38f, 15.94f, 13.21f, 17.38f)
            lineTo(15.41f, 15.18f)
            curveTo(15.69f, 14.9f, 16.08f, 14.82f, 16.43f, 14.94f)
            curveTo(17.55f, 15.31f, 18.75f, 15.51f, 20f, 15.51f)
            curveTo(20.55f, 15.51f, 21f, 15.96f, 21f, 16.51f)
            lineTo(21f, 20f)
            curveTo(21f, 20.55f, 20.55f, 21f, 20f, 21f)
            curveTo(10.61f, 21f, 3f, 13.39f, 3f, 4f)
            curveTo(3f, 3.45f, 3.45f, 3f, 4f, 3f)
            lineTo(7.5f, 3f)
            curveTo(8.05f, 3f, 8.5f, 3.45f, 8.5f, 4f)
            curveTo(8.5f, 5.25f, 8.7f, 6.45f, 9.07f, 7.57f)
            curveTo(9.19f, 7.92f, 9.11f, 8.31f, 8.83f, 8.59f)
            close()
        }
    }

    val Pin: ImageVector by lazy {
        icon("Pin") {
            moveTo(12f, 2f)
            curveTo(8.13f, 2f, 5f, 5.13f, 5f, 9f)
            curveTo(5f, 14.25f, 12f, 22f, 12f, 22f)
            curveTo(12f, 22f, 19f, 14.25f, 19f, 9f)
            curveTo(19f, 5.13f, 15.87f, 2f, 12f, 2f)
            close()
            moveTo(12f, 11.5f)
            curveTo(10.62f, 11.5f, 9.5f, 10.38f, 9.5f, 9f)
            curveTo(9.5f, 7.62f, 10.62f, 6.5f, 12f, 6.5f)
            curveTo(13.38f, 6.5f, 14.5f, 7.62f, 14.5f, 9f)
            curveTo(14.5f, 10.38f, 13.38f, 11.5f, 12f, 11.5f)
            close()
        }
    }

    val Save: ImageVector by lazy {
        icon("Save") {
            moveTo(17f, 3f); lineTo(5f, 3f)
            curveTo(3.9f, 3f, 3f, 3.9f, 3f, 5f)
            lineTo(3f, 19f)
            curveTo(3f, 20.1f, 3.9f, 21f, 5f, 21f)
            lineTo(19f, 21f)
            curveTo(20.1f, 21f, 21f, 20.1f, 21f, 19f)
            lineTo(21f, 7f)
            close()
            moveTo(12f, 19f)
            curveTo(10.34f, 19f, 9f, 17.66f, 9f, 16f)
            curveTo(9f, 14.34f, 10.34f, 13f, 12f, 13f)
            curveTo(13.66f, 13f, 15f, 14.34f, 15f, 16f)
            curveTo(15f, 17.66f, 13.66f, 19f, 12f, 19f)
            close()
            moveTo(15f, 9f); lineTo(5f, 9f); lineTo(5f, 5f); lineTo(15f, 5f)
            close()
        }
    }

    val Trash: ImageVector by lazy {
        icon("Trash") {
            moveTo(6f, 7f); lineTo(6f, 20f)
            curveTo(6f, 21.1f, 6.9f, 22f, 8f, 22f)
            lineTo(16f, 22f)
            curveTo(17.1f, 22f, 18f, 21.1f, 18f, 20f)
            lineTo(18f, 7f)
            close()
            moveTo(9f, 4f); lineTo(15f, 4f); lineTo(16f, 5f)
            lineTo(20f, 5f); lineTo(20f, 7f); lineTo(4f, 7f)
            lineTo(4f, 5f); lineTo(8f, 5f)
            close()
        }
    }

    val ChevronDown: ImageVector by lazy {
        icon("ChevronDown") {
            moveTo(7.41f, 8.59f)
            lineTo(12f, 13.17f)
            lineTo(16.59f, 8.59f)
            lineTo(18f, 10f)
            lineTo(12f, 16f)
            lineTo(6f, 10f)
            close()
        }
    }

    val Building: ImageVector by lazy {
        icon("Building") {
            moveTo(6f, 2f); lineTo(18f, 2f); lineTo(18f, 22f); lineTo(6f, 22f)
            close()
            moveTo(9f, 5f); lineTo(11f, 5f); lineTo(11f, 7f); lineTo(9f, 7f)
            close()
            moveTo(13f, 5f); lineTo(15f, 5f); lineTo(15f, 7f); lineTo(13f, 7f)
            close()
            moveTo(9f, 9f); lineTo(11f, 9f); lineTo(11f, 11f); lineTo(9f, 11f)
            close()
            moveTo(13f, 9f); lineTo(15f, 9f); lineTo(15f, 11f); lineTo(13f, 11f)
            close()
            moveTo(9f, 13f); lineTo(11f, 13f); lineTo(11f, 15f); lineTo(9f, 15f)
            close()
            moveTo(13f, 13f); lineTo(15f, 13f); lineTo(15f, 15f); lineTo(13f, 15f)
            close()
            moveTo(10f, 18f); lineTo(14f, 18f); lineTo(14f, 22f); lineTo(10f, 22f)
            close()
        }
    }

    val Cake: ImageVector by lazy {
        icon("Cake") {
            moveTo(11f, 2f); lineTo(13f, 2f); lineTo(13f, 5f); lineTo(11f, 5f)
            close()
            moveTo(4f, 11f)
            curveTo(4f, 9.34f, 5.34f, 8f, 7f, 8f)
            curveTo(8.1f, 8f, 9.05f, 8.59f, 9.57f, 9.46f)
            curveTo(10.09f, 8.59f, 11.04f, 8f, 12f, 8f)
            curveTo(12.96f, 8f, 13.91f, 8.59f, 14.43f, 9.46f)
            curveTo(14.95f, 8.59f, 15.9f, 8f, 17f, 8f)
            curveTo(18.66f, 8f, 20f, 9.34f, 20f, 11f)
            lineTo(20f, 13f); lineTo(4f, 13f)
            close()
            moveTo(4f, 15f); lineTo(20f, 15f); lineTo(20f, 20f)
            curveTo(20f, 21.1f, 19.1f, 22f, 18f, 22f)
            lineTo(6f, 22f)
            curveTo(4.9f, 22f, 4f, 21.1f, 4f, 20f)
            close()
        }
    }

    val Refresh: ImageVector by lazy {
        icon("Refresh") {
            moveTo(12f, 4f)
            curveTo(16.42f, 4f, 20f, 7.58f, 20f, 12f)
            curveTo(20f, 13.68f, 19.48f, 15.24f, 18.6f, 16.52f)
            lineTo(17f, 15f)
            curveTo(17.63f, 14.09f, 18f, 13.09f, 18f, 12f)
            curveTo(18f, 8.69f, 15.31f, 6f, 12f, 6f)
            close()
            moveTo(12f, 8f)
            lineTo(12f, 3f)
            lineTo(7f, 8f)
            lineTo(12f, 13f)
            close()
            moveTo(6f, 12f)
            curveTo(6f, 15.31f, 8.69f, 18f, 12f, 18f)
            lineTo(12f, 20f)
            curveTo(7.58f, 20f, 4f, 16.42f, 4f, 12f)
            curveTo(4f, 10.32f, 4.52f, 8.76f, 5.4f, 7.48f)
            lineTo(7f, 9f)
            curveTo(6.37f, 9.91f, 6f, 10.91f, 6f, 12f)
            close()
        }
    }

    val Info: ImageVector by lazy {
        icon("Info") {
            moveTo(12f, 2f)
            curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f)
            curveTo(2f, 17.52f, 6.48f, 22f, 12f, 22f)
            curveTo(17.52f, 22f, 22f, 17.52f, 22f, 12f)
            curveTo(22f, 6.48f, 17.52f, 2f, 12f, 2f)
            close()
            moveTo(13f, 17f); lineTo(11f, 17f); lineTo(11f, 11f); lineTo(13f, 11f)
            close()
            moveTo(13f, 9f); lineTo(11f, 9f); lineTo(11f, 7f); lineTo(13f, 7f)
            close()
        }
    }

    val ChevronRight: ImageVector by lazy {
        icon("ChevronRight") {
            moveTo(8.59f, 16.59f)
            lineTo(13.17f, 12f)
            lineTo(8.59f, 7.41f)
            lineTo(10f, 6f)
            lineTo(16f, 12f)
            lineTo(10f, 18f)
            close()
        }
    }
}
