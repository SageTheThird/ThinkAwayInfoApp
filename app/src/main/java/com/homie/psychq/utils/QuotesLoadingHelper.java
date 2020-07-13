package com.homie.psychq.utils;

import com.homie.psychq.BaseApplication;
import com.homie.psychq.R;

/*
* Responsible For Picking Quote FOr Loading
*
* */
public class QuotesLoadingHelper {

    public static final String QUOTE_INDEX  = BaseApplication.get().getString(R.string.quoteIndex);

    public QuotesLoadingHelper() {
    }


    public static int getSequentialIndexForQuote(SharedPreferences sharedPreferences){


        //Or we can create a list of 100 quotes and start getting the quotes from index 0 and when
        //it reaches 100, we reset the quoteIndex
        //each quoteIndex returned will be incremented and stored in prefs
        /*What i want - when quote is requested for the same post again in short interval, it should
        * return the same earlier quote */

        int quoteIndex = sharedPreferences.getIntPref(QUOTE_INDEX,0);

        incrementQuoteIndexAndSave(sharedPreferences,quoteIndex);
//        List<Integer> numbers = new ArrayList<>();
//        for(int i=0;i<100;i++){
//            numbers.add(i);
//        }
//
//        Collections.shuffle(numbers);
        return quoteIndex;
    }

    private static void incrementQuoteIndexAndSave(SharedPreferences sharedPreferences, int quoteIndex) {
        //if index reaches 100, we reset
        int incrementedIndex = quoteIndex+1;
        if(incrementedIndex == 100){
            incrementedIndex = 0;
        }

        sharedPreferences.saveIntPref(QUOTE_INDEX,incrementedIndex);
    }


    public static String getQuoteForInt(int index){
        if (index == 0) {
            return "The majority of my patients consisted not of believers but of those who had lost their faith.";
        } else if (index == 1) {
            return "If the path before you is clear, you’re probably on someone else’s.";
        } else if (index == 2) {
            return "The true leader is always led.";
        } else if (index == 3) {
            return "When an inner situation is not made conscious it appears outside as fate.";
        } else if (index == 4) {
            return "If one does not understand a person, one tends to regard him as a fool.";
        } else if (index == 5) {
            return "The reason for evil in the world is that people are not able to tell their stories.";
        } else if (index == 6) {
            return "Nothing has a stronger influence psychologically on their environment and especially on their children than the un lived life of the parent.";
        } else if (index == 7) {
            return "In all chaos there is a cosmos, in all disorder a secret order.";
        } else if (index == 8) {
            return "There's no coming to consciousness without pain.";
        }else if (index == 9) {
            return "People will do anything, no matter how absurd, to avoid facing their own souls.";
        } else if (index == 10) {
            return "The pendulum of the mind oscillates between sense and nonsense, not between right and wrong.";
        } else if (index == 11) {
            return "Loneliness does not come from having no people about one, but from being unable to communicate the things that seem important to oneself.";
        } else if (index == 12) {
            return "You are what you do, not what you say you'll do.";
        } else if (index == 13) {
            return "Everything that irritates us about others can lead us to an understanding of ourselves.\n\n― Carl Jung";
        } else if (index == 14) {
            return "There is nothing that is going to make people hate you more, and love you more, than telling the truth.\n\n― Stefan Molyneux";
        } else if (index == 15) {
            return "I have a theory that the truth is never told during the nine-to-five hours.\n\n― Hunter S. Thompson";
        } else if (index == 16) {
            return "Boredom: the desire for desires.\n\n― Leo Tolstoy";
        }else if (index == 17) {
            return "Things come apart so easily when they have been held together with lies.\n\n― Dorothy Allison";
        } else if (index == 18) {
            return "Three things can not hide for long: the Moon, the Sun and the Truth.\n\n― Gautama Buddha";
        } else if (index == 19) {
            return "Love truth, but pardon error.\n\n― Voltaire";
        } else if (index == 20) {
            return "The most common form of despair is not being who you are.\n\n― Søren Kierkegaard";
        } else if (index == 21) {
            return "The truth is not always beautiful, nor beautiful words the truth.\n\n― Lao Tzu";
        } else if (index == 22) {
            return "The truth isn't always beauty, but the hunger for it is.\n\n― Nadine Gordimer";
        } else if (index == 23) {
            return "People often claim to hunger for truth, but seldom like the taste when it's served up.\n\n― George Martin";
        } else if (index == 24) {
            return "If you want to tell people the truth, make them laugh, otherwise they'll kill you.\n\n― George Bernard Shaw";
        }else if (index == 25) {
            return "All you have to do is write one true sentence. Write the truest sentence that you know.\n\n― Ernest Hemingway";
        } else if (index == 26) {
            return "Art is the lie that enables us to realize the truth.\n\n― Pablo Picasso";
        } else if (index == 27) {
            return "Rather than love, than money, than fame, give me truth.\n\n― Henry David Thoreau";
        } else if (index == 28) {
            return "A thing is not necessarily true because a man dies for it.\n\n― Oscar Wilde";
        } else if (index == 29) {
            return "In a time of deceit telling the truth is a revolutionary act.\n\n― George Orwell";
        } else if (index == 30) {
            return "Above all, don't lie to yourself.\n\n― Fyodor Dostoevsky";
        } else if (index == 31) {
            return "The truth will set you free, but first it will piss you off.\n\n― Joe Klaas";
        } else if (index == 32) {
            return "Compassion is the basis of morality.";
        }else if (index == 33) {
            return "The person who writes for fools is always sure of a large audience.";
        } else if (index == 34) {
            return "It is difficult to find happiness within oneself, but it is impossible to find it anywhere else.";
        } else if (index == 35) {
            return "Compassion for animals is intimately associated with goodness of character, and it may be confidently asserted that he who is cruel to animals cannot be a good man.";
        } else if (index == 36) {
            return "One should use common words to say uncommon things.";
        } else if (index == 37) {
            return "A sense of humour is the only divine quality of man.";
        } else if (index == 39) {
            return "The cheapest sort of pride is national pride; for if a man is proud of his own nation, it argues that he has no qualities of his own of which he can be proud.";
        } else if (index == 40) {
            return "Every parting gives a foretaste of death, every reunion a hint of the resurrection.";
        } else if (index == 41) {
            return "The safest way of not being very miserable is not to expect to be very happy.";
        }else if (index == 42) {
            return "Change alone is eternal, perpetual, immortal.";
        } else if (index == 43) {
            return "Ignorance, the root and stem of every evil.\n\n― Arthur Schopenhauer";
        } else if (index == 44) {
            return "Good people do not need laws to tell them to act responsibly, while bad people will find a way around the laws.\n\n― Arthur Schopenhauer";
        } else if (index == 45) {
            return "Those who tell the stories rule society.\n\n― Arthur Schopenhauer";
        } else if (index == 46) {
            return "You should not honor men more than truth.\n\n― Arthur Schopenhauer";
        } else if (index == 47) {
            return "Courage is knowing what not to fear.\n\n― Arthur Schopenhauer";
        } else if (index == 48) {
            return "There are two things a person should never be angry at, what they can help, and what they cannot.\n\n― Arthur Schopenhauer";
        } else if (index == 49) {
            return "...throw roses into the abyss and say: 'here is my thanks to the monster who didn't succeed in swallowing me alive.\n\n― Plato";
        }else if (index == 50) {
            return "Distrust all in whom the impulse to punish is powerful.\n\n― Plato";
        } else if (index == 51) {
            return "The voice of beauty speaks softly; it creeps only into the most fully awakened souls.\n\n― Plato";
        } else if (index == 52) {
            return "Talking much about oneself can also be a means to conceal oneself.\n\n― Plato";
        } else if (index == 53) {
            return "Everything the State says is a lie, and everything it has it has stolen.\n\n― Plato";
        } else if (index == 54) {
            return "Is life not a thousand times too short for us to bore ourselves?\n\n― Plato";
        } else if (index == 55) {
            return "Hope in reality is the worst of all evils because it prolongs the torments of man.\n\n― Plato";
        } else if (index == 56) {
            return "There is more wisdom in your body than in your deepest philosophy.\n\n― Plato";
        } else if (index == 57) {
            return "Is it better to out-monster the monster or to be quietly devoured?\n\n― Plato";
        }else if (index == 58) {
            return "One repays a teacher badly if one always remains nothing but a pupil.\n\n― Plato";
        } else if (index == 59) {
            return "They muddy the water, to make it seem deep.\n\n― Plato";
        } else if (index == 60) {
            return "A thought, even a possibility, can shatter and transform us.\n\n― Plato";
        } else if (index == 61) {
            return "Perhaps I know best why it is man alone who laughs; he alone suffers so deeply that he had to invent laughter.\n\n― Plato";
        } else if (index == 62) {
            return "What does your conscience say? — 'You should become the person you are'.\n\n― Plato";
        } else if (index == 63) {
            return "There is not enough love and goodness in the world to permit giving any of it away to imaginary beings.\n\n― Plato";
        } else if (index == 64) {
            return "Silence is worse; all truths that are kept silent become poisonous.\n\n― Plato";
        } else if (index == 65) {
            return "In truth,there was only one christian and he died on the cross.\n\n― Plato";
        }else if (index == 66) {
            return "One ought to hold on to one's heart; for if one lets it go, one soon loses control of the head too.\n\n― Plato";
        } else if (index == 67) {
            return "All truly great thoughts are conceived while walking.\n\n― Plato";
        } else if (index == 68) {
            return "We have art in order not to die of the truth.\n\n― Plato";
        } else if (index == 69) {
            return "Faith: not wanting to know what the truth is.\n\n― Plato";
        } else if (index == 70) {
            return "The thought of suicide is a great consolation: by means of it one gets through many a dark night.\n\n― Plato";
        } else if (index == 71) {
            return "Thoughts are the shadows of our feelings -- always darker, emptier and simpler.\n\n― Plato";
        } else if (index == 72) {
            return "There are no facts, only interpretations.\n\n― Plato";
        } else if (index == 73) {
            return "That which does not kill us makes us stronger.\n\n― Nietzsche";
        }else if (index == 74) {
            return "";
        } else if (index == 75) {
            return "";
        } else if (index == 76) {
            return "";
        } else if (index == 77) {
            return "";
        } else if (index == 78) {
            return "";
        } else if (index == 79) {
            return "";
        } else if (index == 80) {
            return "";
        } else if (index == 81) {
            return "";
        }else if (index == 82) {
            return "";
        } else if (index == 83) {
            return "";
        } else if (index == 84) {
            return "";
        } else if (index == 85) {
            return "";
        } else if (index == 86) {
            return "";
        } else if (index == 87) {
            return "";
        } else if (index == 88) {
            return "";
        } else if (index == 89) {
            return "";
        }else if (index == 90) {
            return "";
        } else if (index == 91) {
            return "";
        } else if (index == 92) {
            return "";
        } else if (index == 93) {
            return "";
        } else if (index == 94) {
            return "";
        } else if (index == 95) {
            return "";
        } else if (index == 96) {
            return "";
        } else if (index == 97) {
            return "";
        }else if (index == 98) {
            return "";
        } else if (index == 99) {
            return "";
        }

        return "Default";
    }
}
