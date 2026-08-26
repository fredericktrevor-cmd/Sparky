package com.example.data.scenarios

import com.example.data.model.ChoiceOption
import com.example.data.model.EvidenceBasedParentTip
import com.example.data.model.ScenarioStep
import com.example.data.model.SkillCategory
import com.example.data.model.SocialScenario
import com.example.data.model.StickerItem
import com.example.data.model.TrophyMilestone
import com.example.data.model.VisualCue

object PreloadedScenarios {

    val allScenarios: List<SocialScenario> = listOf(
        SocialScenario(
            id = "playground_turns",
            title = "Taking Turns on the Slide",
            subtitle = "Waiting in line patiently and cheering on our friends.",
            category = SkillCategory.PLAYGROUND,
            imageResName = "img_scenario_playground",
            estimatedMinutes = 4,
            starsReward = 30,
            unlockStickerId = "sticker_turn_taker",
            learningObjective = "Understand turn-taking sequences and positive patience strategies.",
            evidenceBaseNote = "Uses Carol Gray's Social Stories™ 10.2 structure: descriptive, directive, and affirmative sentences for predictable social routines.",
            steps = listOf(
                ScenarioStep(
                    stepIndex = 1,
                    title = "The Slide has a Line!",
                    storyPrompt = "You arrive at the playground slide. Your friend Maya is climbing up the ladder right now.",
                    narrationText = "Look! Maya is getting ready to slide down. There is one person in front of you. What is a kind way to wait?",
                    visualCue = VisualCue(
                        title = "Wait in Line",
                        symbolEmoji = "🧍‍♂️...🧍‍♀️",
                        audioCueText = "Waiting your turn keeps everyone safe and happy.",
                        colorHex = 0xFF10B981,
                        pecsLabel = "WAIT TURN"
                    ),
                    companionSpeech = "I really want to zoom down the slide! But Maya is climbing up. What should I do?",
                    choices = listOf(
                        ChoiceOption(
                            id = "wait_step_line",
                            text = "Stand on the grass and wait for Maya to slide down first",
                            iconEmoji = "⏳",
                            isPositive = true,
                            feedbackTitle = "Awesome Patience!",
                            feedbackDescription = "Maya safely slides down and waves with a big smile! Now the slide is all clear for you.",
                            explanationAudio = "Great choice! Giving Maya space lets her slide safely. When she finishes, it's your turn!",
                            socialTip = "Counting 1 to 5 quietly in your head makes waiting easy!"
                        ),
                        ChoiceOption(
                            id = "push_ahead",
                            text = "Climb up the ladder right behind Maya without waiting",
                            iconEmoji = "🏃",
                            isPositive = false,
                            feedbackTitle = "Let's Try Again Gently",
                            feedbackDescription = "Climbing too close might bump Maya or make her feel crowded.",
                            explanationAudio = "When we climb too close, friends can feel startled. Standing one step back gives everyone safe room.",
                            socialTip = "Giving one arm's distance keeps everyone comfortable."
                        )
                    ),
                    peerExpression = "😊",
                    peerName = "Maya",
                    sensoryTip = "If waiting makes your feet want to wiggle, you can do 3 gentle toe taps!"
                ),
                ScenarioStep(
                    stepIndex = 2,
                    title = "Cheering for Maya",
                    storyPrompt = "Maya reaches the top of the slide and is getting ready to slide down!",
                    narrationText = "Maya is smiling at the top. How can we encourage her?",
                    visualCue = VisualCue(
                        title = "Friendly Words",
                        symbolEmoji = "👏",
                        audioCueText = "Saying kind words makes our friends feel happy!",
                        colorHex = 0xFF3B82F6,
                        pecsLabel = "CHEER FRIEND"
                    ),
                    companionSpeech = "Maya is ready to zoom down! Let's say something encouraging!",
                    choices = listOf(
                        ChoiceOption(
                            id = "say_go_maya",
                            text = "Say: 'Have fun Maya! Wheee!' with a smile",
                            iconEmoji = "🎉",
                            isPositive = true,
                            feedbackTitle = "Super Kind Word!",
                            feedbackDescription = "Maya giggles and zooms down! She says, 'Thanks! Your turn now!'",
                            explanationAudio = "Cheering for friends makes playground games super fun for everyone!",
                            socialTip = "A warm voice tone helps friends feel welcome."
                        ),
                        ChoiceOption(
                            id = "yell_hurry",
                            text = "Yell loudly: 'Hurry up, you are too slow!'",
                            iconEmoji = "📢",
                            isPositive = false,
                            feedbackTitle = "Soft Voice Hint",
                            feedbackDescription = "Loud rushing words might make Maya feel nervous or rushed.",
                            explanationAudio = "Friends go down best when they feel safe. Gentle encouragement works wonders!",
                            socialTip = "Using a soft inside voice helps keep everyone calm."
                        )
                    ),
                    peerExpression = "😄",
                    peerName = "Maya"
                ),
                ScenarioStep(
                    stepIndex = 3,
                    title = "Your Turn to Zoom!",
                    storyPrompt = "Maya stepped away from the bottom of the slide. The slide is completely clear for you!",
                    narrationText = "Now it is your turn! You climb the ladder safely and reach the top.",
                    visualCue = VisualCue(
                        title = "My Turn Now",
                        symbolEmoji = "🌟",
                        audioCueText = "The path is clear. It is your turn to have fun!",
                        colorHex = 0xFFF59E0B,
                        pecsLabel = "MY TURN"
                    ),
                    companionSpeech = "Yay! We waited nicely, Maya is cheering for us, and now it's our turn!",
                    choices = listOf(
                        ChoiceOption(
                            id = "slide_safely",
                            text = "Sit feet first and slide down with a joyful cheer!",
                            iconEmoji = "🛝",
                            isPositive = true,
                            feedbackTitle = "Woohoo! What a Ride!",
                            feedbackDescription = "You slide down safely! Maya gives you a high five. You both took great turns.",
                            explanationAudio = "Fantastic turn-taking! You and Maya practiced super playground teamwork.",
                            socialTip = "Taking turns means everyone gets to enjoy the fun!"
                        ),
                        ChoiceOption(
                            id = "stay_at_top",
                            text = "Sit at the top and refuse to let anyone else use the slide",
                            iconEmoji = "🛑",
                            isPositive = false,
                            feedbackTitle = "Sharing the Fun",
                            feedbackDescription = "Other friends are waiting at the bottom eager for their turn too.",
                            explanationAudio = "When we slide down, we leave room for the next friend to enjoy it just like you did!",
                            socialTip = "Sliding down promptly keeps the playground flow joyful."
                        )
                    ),
                    peerExpression = "🙌",
                    peerName = "Maya"
                )
            )
        ),
        SocialScenario(
            id = "classroom_circle_time",
            title = "Classroom Circle Time",
            subtitle = "Listening with care and raising our hand to share ideas.",
            category = SkillCategory.CLASSROOM,
            imageResName = "img_scenario_classroom",
            estimatedMinutes = 5,
            starsReward = 35,
            unlockStickerId = "sticker_classroom_star",
            learningObjective = "Practice whole-body listening, stim-friendly comfort, and hand-raising in group settings.",
            evidenceBaseNote = "Aligned with UDL (Universal Design for Learning) and neurodiversity-affirming whole body listening (listening looks different for every unique brain).",
            steps = listOf(
                ScenarioStep(
                    stepIndex = 1,
                    title = "Finding a Cozy Spot",
                    storyPrompt = "Teacher Alex rings the gentle chime for circle time story hour. Kids are sitting on the colorful rug.",
                    narrationText = "Circle time is starting! You have your soft fidget ring in your hand. Where can you sit comfortably?",
                    visualCue = VisualCue(
                        title = "Cozy Circle Spot",
                        symbolEmoji = "🧘‍♂️",
                        audioCueText = "Finding your space helps your body feel grounded.",
                        colorHex = 0xFF3B82F6,
                        pecsLabel = "CIRCLE TIME"
                    ),
                    companionSpeech = "I love stories! Let's find a cozy cushion so we can listen nicely.",
                    choices = listOf(
                        ChoiceOption(
                            id = "sit_on_cushion",
                            text = "Sit on a blue star cushion with your quiet fidget ring",
                            iconEmoji = "🪑",
                            isPositive = true,
                            feedbackTitle = "Cozy and Ready!",
                            feedbackDescription = "You sit comfortably. Your fidget ring keeps your hands happily occupied while listening.",
                            explanationAudio = "Wonderful! Sitting in your spot and using a quiet fidget helps your brain focus on the story.",
                            socialTip = "Quiet fidgets are great tools for focusing."
                        ),
                        ChoiceOption(
                            id = "run_around_circle",
                            text = "Run laps around the rug while teacher is reading the book",
                            iconEmoji = "🏃‍♂️",
                            isPositive = false,
                            feedbackTitle = "Let's Find a Body Pause",
                            feedbackDescription = "Running might bump friends or make it hard for everyone to hear the story.",
                            explanationAudio = "If your body has lots of wiggles, holding a cushion tight or doing slow shoulder rolls can help!",
                            socialTip = "If you need movement, ask teacher for a gentle wiggle break."
                        )
                    ),
                    peerExpression = "📖",
                    peerName = "Teacher Alex",
                    sensoryTip = "Holding a weighted lap cushion or squeezing a soft stress ball is great for body grounding."
                ),
                ScenarioStep(
                    stepIndex = 2,
                    title = "You Have an Idea to Share!",
                    storyPrompt = "Teacher Alex asks: 'Who knows what animal lives in the ocean and has eight arms?' You know the answer!",
                    narrationText = "You know it is an octopus! You feel very excited to share. How do we let Teacher Alex know?",
                    visualCue = VisualCue(
                        title = "Raise Your Hand",
                        symbolEmoji = "✋",
                        audioCueText = "Raising our hand signals the teacher politely.",
                        colorHex = 0xFFF59E0B,
                        pecsLabel = "RAISE HAND"
                    ),
                    companionSpeech = "I know this one! It's an octopus! How should we let teacher know?",
                    choices = listOf(
                        ChoiceOption(
                            id = "raise_quiet_hand",
                            text = "Raise your hand high and quietly wait to be called",
                            iconEmoji = "🙋",
                            isPositive = true,
                            feedbackTitle = "Super Polite Signal!",
                            feedbackDescription = "Teacher Alex smiles, points to you and says: 'Alex, what animal is it?'",
                            explanationAudio = "Raising your hand shows great classroom teamwork! Teacher Alex was happy to hear your answer.",
                            socialTip = "Raising a quiet hand lets teacher see you right away."
                        ),
                        ChoiceOption(
                            id = "shout_answer",
                            text = "Shout: 'OCTOPUS! OCTOPUS! LOOK AT ME!' very loudly",
                            iconEmoji = "🗣️",
                            isPositive = false,
                            feedbackTitle = "Gentle Voice Reminder",
                            feedbackDescription = "Shouting might surprise friends or interrupt someone who was thinking.",
                            explanationAudio = "Raising your hand gives everyone a fair turn to think and speak.",
                            socialTip = "Catching your breath before raising a hand makes sharing awesome!"
                        )
                    ),
                    peerExpression = "🐙",
                    peerName = "Teacher Alex"
                )
            )
        ),
        SocialScenario(
            id = "emotions_calm_corner",
            title = "The Sensory Calm Down Corner",
            subtitle = "Using balloon breathing and cozy sensory tools when overwhelmed.",
            category = SkillCategory.EMOTIONS_CALM,
            imageResName = "img_scenario_calm_corner",
            estimatedMinutes = 4,
            starsReward = 30,
            unlockStickerId = "sticker_calm_champion",
            learningObjective = "Recognize sensory overwhelm triggers and self-advocate with calming strategies.",
            evidenceBaseNote = "Based on Zones of Regulation™ (Leah Kuypers) and sensory diet accommodation protocols for neurodivergent children.",
            steps = listOf(
                ScenarioStep(
                    stepIndex = 1,
                    title = "The Room Feels Loud",
                    storyPrompt = "During indoor recess, the music is playing and several kids are laughing loudly. Your head feels buzzing and overwhelmed.",
                    narrationText = "The room is very loud right now. Your brain feels wobbly in the Yellow or Red Zone. What tool can help you feel calm?",
                    visualCue = VisualCue(
                        title = "Check My Zone",
                        symbolEmoji = "⚡➡️🧘",
                        audioCueText = "It is okay to feel overwhelmed. We have calming tools!",
                        colorHex = 0xFF8B5CF6,
                        pecsLabel = "CALM BREAK"
                    ),
                    companionSpeech = "Whoa, everything is so noisy! My ears feel tired. What should I do to feel cozy?",
                    choices = listOf(
                        ChoiceOption(
                            id = "put_on_headphones_corner",
                            text = "Put on noise-cancelling headphones and walk to the Calm Corner beanbag",
                            iconEmoji = "🎧",
                            isPositive = true,
                            feedbackTitle = "Brilliant Self-Advocacy!",
                            feedbackDescription = "The headphones make the noise soft and quiet. The soft beanbag feels like a warm hug.",
                            explanationAudio = "Super choice! Taking a sensory break helps your nervous system reset and feel peaceful.",
                            socialTip = "Wearing headphones or using a calm corner is a healthy superhero superpower!"
                        ),
                        ChoiceOption(
                            id = "screaming_cover_ears",
                            text = "Scream and throw crayons on the floor",
                            iconEmoji = "🗯️",
                            isPositive = false,
                            feedbackTitle = "Gentle Reset Opportunity",
                            feedbackDescription = "Throwing things might break supplies or scare friends nearby.",
                            explanationAudio = "When sounds are too big, asking for headphones or walking to the calm corner protects our body safely.",
                            socialTip = "You can show your teacher the 'I need a break' visual card anytime."
                        )
                    ),
                    peerExpression = "😌",
                    peerName = "Companion Spark",
                    sensoryTip = "Noise-cancelling headphones and deep pressure cushions are awesome sensory tools."
                ),
                ScenarioStep(
                    stepIndex = 2,
                    title = "3 Deep Balloon Breaths",
                    storyPrompt = "You are sitting in the cozy calm corner under the soft star nightlight.",
                    narrationText = "Let's do 3 deep balloon breaths together. Breathe in through your nose, expand your belly like a balloon, and blow out gently.",
                    visualCue = VisualCue(
                        title = "Balloon Breathing",
                        symbolEmoji = "🎈",
                        audioCueText = "Inhale calm... Exhale gentle breeze.",
                        colorHex = 0xFF0D9488,
                        pecsLabel = "DEEP BREATH"
                    ),
                    companionSpeech = "Let's breathe together! Smell the flower... now blow out the birthday candle!",
                    choices = listOf(
                        ChoiceOption(
                            id = "do_balloon_breaths",
                            text = "Take 3 slow, deep belly breaths with Spark",
                            iconEmoji = "🌬️",
                            isPositive = true,
                            feedbackTitle = "Ahhh... Feeling Calm!",
                            feedbackDescription = "Your heart beats slower, your shoulders relax, and you move back into the Green Zone!",
                            explanationAudio = "Great job! Deep breathing sends a signal to your body that you are safe and in control.",
                            socialTip = "Deep breathing works anywhere—at school, at home, or in the car!"
                        ),
                        ChoiceOption(
                            id = "hold_breath_tense",
                            text = "Tense your whole body and hold your breath tight",
                            iconEmoji = "😣",
                            isPositive = false,
                            feedbackTitle = "Let the Air Flow",
                            feedbackDescription = "Holding breath can make our tummy feel tight.",
                            explanationAudio = "Letting out a gentle 'whoosh' breath melts the tension away like warm butter.",
                            socialTip = "Try blowing imaginary soap bubbles to make breathing fun!"
                        )
                    ),
                    peerExpression = "✨",
                    peerName = "Companion Spark"
                )
            )
        ),
        SocialScenario(
            id = "asking_to_join_game",
            title = "Asking to Join a Game",
            subtitle = "Finding the right words to play together with new friends.",
            category = SkillCategory.CONVERSATION,
            imageResName = "img_scenario_playground",
            estimatedMinutes = 4,
            starsReward = 30,
            unlockStickerId = "sticker_friendship_wizard",
            learningObjective = "Develop verbal and non-verbal joining scripts ('Can I play too?') and respect peer responses.",
            evidenceBaseNote = "Social scripts and peer interaction modeling grounded in Evidence-Based Social Skills Training (SST) for ASD.",
            steps = listOf(
                ScenarioStep(
                    stepIndex = 1,
                    title = "Friends are Building a Tower",
                    storyPrompt = "Leo and Sam are on the rug building a huge castle made of colorful wooden blocks.",
                    narrationText = "You see Leo and Sam building a cool castle. You have a toy dragon that would fit perfectly! How can you ask to join?",
                    visualCue = VisualCue(
                        title = "Ask to Play",
                        symbolEmoji = "🏰🤝",
                        audioCueText = "Using friendly words invites friends to share the fun.",
                        colorHex = 0xFFF59E0B,
                        pecsLabel = "CAN I PLAY?"
                    ),
                    companionSpeech = "That castle looks amazing! Let's see if we can build together with our dragon!",
                    choices = listOf(
                        ChoiceOption(
                            id = "ask_can_i_play",
                            text = "Walk up with a smile and ask: 'Hi! Can I play blocks with you too?'",
                            iconEmoji = "💬",
                            isPositive = true,
                            feedbackTitle = "Friendly Invitation!",
                            feedbackDescription = "Leo smiles and says: 'Sure! You can guard the castle gate with your dragon!'",
                            explanationAudio = "Awesome communication! Asking politely with a friendly smile makes friends happy to share.",
                            socialTip = "Giving a gentle smile and waiting for an answer shows respect."
                        ),
                        ChoiceOption(
                            id = "grab_blocks_without_asking",
                            text = "Walk up and take three blocks from their tower without saying anything",
                            iconEmoji = "🖐️",
                            isPositive = false,
                            feedbackTitle = "Sharing Words First",
                            feedbackDescription = "Taking blocks might knock down their hard work or startle them.",
                            explanationAudio = "When we ask first, friends understand our kind intentions and love to build together!",
                            socialTip = "Using your words or visual communication card lets friends know your plan."
                        )
                    ),
                    peerExpression = "😃",
                    peerName = "Leo & Sam"
                )
            )
        ),
        SocialScenario(
            id = "personal_space_bubble",
            title = "My Personal Space Bubble",
            subtitle = "Understanding body boundaries and comfortable friend distance.",
            category = SkillCategory.CONVERSATION,
            imageResName = "img_scenario_classroom",
            estimatedMinutes = 4,
            starsReward = 30,
            unlockStickerId = "sticker_space_hero",
            learningObjective = "Visualize an invisible hula-hoop personal space bubble for healthy physical boundaries.",
            evidenceBaseNote = "Visual boundary representation (Personal Space Camp methodology / Michelle Garcia Winner's Social Thinking®).",
            steps = listOf(
                ScenarioStep(
                    stepIndex = 1,
                    title = "The Invisible Hula-Hoop",
                    storyPrompt = "Everyone has an invisible bubble around their body—about the size of an outstretched arm.",
                    narrationText = "When talking to your friend Mia, keeping an arm's length distance helps both of you feel comfortable and respected.",
                    visualCue = VisualCue(
                        title = "Space Bubble",
                        symbolEmoji = "🫧🧍🫧",
                        audioCueText = "One arm's length keeps everyone comfortable.",
                        colorHex = 0xFF10B981,
                        pecsLabel = "PERSONAL SPACE"
                    ),
                    companionSpeech = "Imagine you are inside a shimmering soap bubble! Let's keep a friendly distance.",
                    choices = listOf(
                        ChoiceOption(
                            id = "give_arm_distance",
                            text = "Stand one arm's length away, look towards Mia, and say 'Hi Mia!'",
                            iconEmoji = "📏",
                            isPositive = true,
                            feedbackTitle = "Comfortable Space!",
                            feedbackDescription = "Mia smiles comfortably and says: 'Hi! I love your robot shirt!'",
                            explanationAudio = "Great body awareness! Giving space helps everyone feel safe, relaxed, and ready to talk.",
                            socialTip = "An arm's length is like an invisible gentle hug space."
                        ),
                        ChoiceOption(
                            id = "stand_inches_from_face",
                            text = "Stand 2 inches away from Mia's nose while talking",
                            iconEmoji = "👀",
                            isPositive = false,
                            feedbackTitle = "Space Bubble Reminder",
                            feedbackDescription = "Standing very close might feel crowded or overwhelming for Mia.",
                            explanationAudio = "Stepping back one small step gives Mia plenty of breathing room to chat happily!",
                            socialTip = "If you're not sure, you can extend your arm gently to test the bubble distance."
                        )
                    ),
                    peerExpression = "😊",
                    peerName = "Mia"
                )
            )
        )
    )

    val allStickers: List<StickerItem> = listOf(
        StickerItem(
            id = "sticker_turn_taker",
            name = "Turn-Taker Medal",
            emoji = "🥇",
            colorHex = 0xFF10B981,
            category = "Playground",
            description = "Awarded for practicing patient turn-taking on the slide!",
            isUnlocked = true,
            unlockScenarioId = "playground_turns"
        ),
        StickerItem(
            id = "sticker_classroom_star",
            name = "Classroom Star",
            emoji = "⭐",
            colorHex = 0xFF3B82F6,
            category = "Classroom",
            description = "Awarded for super whole-body listening and raising your hand!",
            isUnlocked = false,
            unlockScenarioId = "classroom_circle_time"
        ),
        StickerItem(
            id = "sticker_calm_champion",
            name = "Calm Superhero",
            emoji = "🧘",
            colorHex = 0xFF8B5CF6,
            category = "Emotions",
            description = "Master of deep balloon breaths and sensory calm corner tools!",
            isUnlocked = false,
            unlockScenarioId = "emotions_calm_corner"
        ),
        StickerItem(
            id = "sticker_friendship_wizard",
            name = "Friendship Wizard",
            emoji = "🤝",
            colorHex = 0xFFF59E0B,
            category = "Social",
            description = "Knows the magic words: 'Can I play too?'",
            isUnlocked = false,
            unlockScenarioId = "asking_to_join_game"
        ),
        StickerItem(
            id = "sticker_space_hero",
            name = "Space Bubble Hero",
            emoji = "🫧",
            colorHex = 0xFF06B6D4,
            category = "Social",
            description = "Master of respecting personal space boundaries and gentle distance!",
            isUnlocked = false,
            unlockScenarioId = "personal_space_bubble"
        ),
        StickerItem(
            id = "sticker_rainbow_heart",
            name = "Empathy Heart",
            emoji = "💖",
            colorHex = 0xFFEC4899,
            category = "Feelings",
            description = "Noticing how friends feel and sharing kind encouragement!",
            isUnlocked = true,
            unlockScenarioId = null
        ),
        StickerItem(
            id = "sticker_dino_explorer",
            name = "Dino Explorer",
            emoji = "🦖",
            colorHex = 0xFF16A34A,
            category = "Fun",
            description = "Bravery badge for trying new social routines!",
            isUnlocked = false,
            unlockScenarioId = null
        ),
        StickerItem(
            id = "sticker_cosmic_rocket",
            name = "Cosmic Rocket",
            emoji = "🚀",
            colorHex = 0xFF6366F1,
            category = "Milestones",
            description = "Blasting off to 100 stars and beyond!",
            isUnlocked = false,
            unlockScenarioId = null
        )
    )

    val allTrophies: List<TrophyMilestone> = listOf(
        TrophyMilestone(
            id = "trophy_first_spark",
            title = "First Social Spark",
            description = "Completed your very first interactive social story!",
            emoji = "✨",
            starsRequired = 30,
            isUnlocked = true
        ),
        TrophyMilestone(
            id = "trophy_star_collector",
            title = "Star Collector",
            description = "Earned 60 total learning stars across scenarios.",
            emoji = "🌟",
            starsRequired = 60,
            isUnlocked = false
        ),
        TrophyMilestone(
            id = "trophy_calm_master",
            title = "Calm Master",
            description = "Completed all sensory calm-down and breathing exercises.",
            emoji = "🧘‍♂️",
            starsRequired = 90,
            isUnlocked = false
        ),
        TrophyMilestone(
            id = "trophy_superstar_friend",
            title = "Superstar Friend",
            description = "Mastered all playground, classroom, and conversation skills!",
            emoji = "🏆",
            starsRequired = 150,
            isUnlocked = false
        )
    )

    val parentTips: List<EvidenceBasedParentTip> = listOf(
        EvidenceBasedParentTip(
            id = "tip_social_stories",
            title = "The Power of Social Stories™",
            sourceOrganization = "Carol Gray Center for Social Learning",
            category = SkillCategory.PLAYGROUND,
            summary = "Social Stories provide clear, literal, non-judgmental explanations of what to expect in everyday social situations and why people act the way they do.",
            practicalHomeAction = "Review the 'Taking Turns on the Slide' story right before heading to the neighborhood park to prime positive expectations.",
            iconEmoji = "📖"
        ),
        EvidenceBasedParentTip(
            id = "tip_sensory_breaks",
            title = "Affirming Stimming & Sensory Breaks",
            sourceOrganization = "Autistic Self Advocacy Network (ASAN)",
            category = SkillCategory.EMOTIONS_CALM,
            summary = "Stimming (fidgeting, flapping, rocking) is a healthy, natural self-regulation mechanism that helps neurodivergent brains manage sensory input and focus.",
            practicalHomeAction = "Provide designated quiet fidgets, weighted lap pads, and noise-cancelling headphones freely without requiring eye contact during listening.",
            iconEmoji = "🎧"
        ),
        EvidenceBasedParentTip(
            id = "tip_zones_of_regulation",
            title = "Zones of Regulation in Daily Life",
            sourceOrganization = "Zones of Regulation™ (Leah Kuypers, OTR/L)",
            category = SkillCategory.EMOTIONS_CALM,
            summary = "Emotions are categorized into four colors (Blue, Green, Yellow, Red). All zones are natural—the goal is identifying triggers and choosing calming tools.",
            practicalHomeAction = "Check in before dinner: 'Which zone is your battery in right now?' Practice 3 balloon breaths together when in the Yellow zone.",
            iconEmoji = "🌈"
        ),
        EvidenceBasedParentTip(
            id = "tip_visual_cues_pecs",
            title = "Visual Schedules & Visual Cues",
            sourceOrganization = "Autism Society of America",
            category = SkillCategory.CLASSROOM,
            summary = "Visual cues reduce auditory processing cognitive load by up to 70% in children on the spectrum, transforming abstract expectations into tangible steps.",
            practicalHomeAction = "Use icon-based visual cues for morning routines (brush teeth -> socks -> backpack) matching the app's visual cards.",
            iconEmoji = "🗂️"
        )
    )
}
