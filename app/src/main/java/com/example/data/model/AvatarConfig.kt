package com.example.data.model

enum class AvatarBuddy(
    val id: String,
    val displayName: String,
    val description: String,
    val primaryColor: Long,
    val secondaryColor: Long
) {
    SPARK_ROBOT("spark_robot", "Spark the Robot", "Friendly & logical buddy with glowing antenna!", 0xFF0D9488, 0xFF5EEAD4),
    LEO_LION("leo_lion", "Leo the Brave Lion", "Kind & patient helper with a warm fluffy mane!", 0xFFEA580C, 0xFFFDBA74),
    BELLA_BUNNY("bella_bunny", "Bella the Gentle Bunny", "Super listener with soft ears and a big heart!", 0xFF9333EA, 0xFFE9D5FF),
    SAM_EXPLORER("sam_explorer", "Sam the Star Explorer", "Adventurous pal ready to discover new friends!", 0xFF2563EB, 0xFF93C5FD)
}

enum class AvatarExpression(
    val id: String,
    val label: String,
    val emoji: String,
    val audioHint: String
) {
    HAPPY("happy", "Happy Smile", "😊", "I am feeling cheerful and ready to learn!"),
    PROUD("proud", "Proud Champion", "⭐", "Look at how much I have practiced today!"),
    CALM("calm", "Calm & Relaxed", "😌", "Taking a deep breath and feeling peaceful."),
    CURIOUS("curious", "Curious Explorer", "🧐", "I wonder what friendly things we will do next!"),
    GENTLE("gentle", "Gentle Friend", "🤗", "I am ready to share and take turns nicely.")
}

enum class AvatarOutfit(
    val id: String,
    val label: String,
    val iconName: String,
    val colorHex: Long
) {
    HERO_CAPE("hero_cape", "Hero Cape", "cape", 0xFFE11D48),
    COZY_HOODIE("cozy_hoodie", "Cozy Hoodie", "hoodie", 0xFF0284C7),
    STAR_TEE("star_tee", "Star T-Shirt", "tee", 0xFFF59E0B),
    DINO_SUIT("dino_suit", "Dino Jumpsuit", "dino", 0xFF16A34A),
    RAINBOW_JACKET("rainbow_jacket", "Rainbow Jacket", "rainbow", 0xFF8B5CF6)
}

enum class SensoryAccessory(
    val id: String,
    val label: String,
    val description: String,
    val iconEmoji: String,
    val colorHex: Long
) {
    NOISE_HEADPHONES("noise_headphones", "Noise-Cancelling Headphones", "Helps keep loud sounds soft and comfortable!", "🎧", 0xFF2563EB),
    COOL_GLASSES("cool_glasses", "Sensory Sunglasses", "Softens bright lights so our eyes feel good!", "🕶️", 0xFF0D9488),
    FIDGET_BAND("fidget_band", "Fidget Spark Ring", "Fun to touch when we need a moment of focus!", "💍", 0xFFF59E0B),
    COZY_SCARF("cozy_scarf", "Soft Weighted Scarf", "Gentle warm hug around the neck!", "🧣", 0xFFEC4899),
    NONE("none", "No Accessory", "Just me being myself!", "✨", 0xFF64748B)
}

enum class AuraSparkle(
    val id: String,
    val label: String,
    val colorHex: Long
) {
    SUNSHINE_GOLD("gold", "Sunshine Gold", 0xFFFBBF24),
    MAGIC_CYAN("cyan", "Magic Sparkle Teal", 0xFF2DD4BF),
    GENTLE_PURPLE("purple", "Cosmic Violet", 0xFFA855F7),
    SWEET_CORAL("coral", "Warm Rose", 0xFFFB7185)
}

data class AvatarConfig(
    val kidName: String = "Alex",
    val buddy: AvatarBuddy = AvatarBuddy.SPARK_ROBOT,
    val expression: AvatarExpression = AvatarExpression.HAPPY,
    val outfit: AvatarOutfit = AvatarOutfit.HERO_CAPE,
    val accessory: SensoryAccessory = SensoryAccessory.NOISE_HEADPHONES,
    val aura: AuraSparkle = AuraSparkle.SUNSHINE_GOLD
)
