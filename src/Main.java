import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.FunctionalResultHandler;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.audio.AudioSource;
import org.javacord.api.entity.permission.PermissionType;

import java.util.Random;

public class Main {

    enum SpiritBoxResponse {
         BEHIND,
         CLOSE,
         AWAY,
         HERE,
         FAR,
         ADULT,
         CHILD,
         OLD,
         KID,
         KILL,
         NEXT,
         ATTACK,
         DIE
    }

    private static final AudioPlayerManager manager = new DefaultAudioPlayerManager();

    public static void main(String[] args) {

        String token = System.getenv("DISCORD_TOKEN");
        manager.registerSourceManager(new YoutubeAudioSourceManager(true));

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();
        String link = api.createBotInvite();
        System.out.println("You can invite the bot by using the following url: " + link);

        api.addMessageCreateListener(event -> {
            String message = event.getMessageContent().toLowerCase();

            event.getServer().ifPresent(server -> {
                if (message.matches("!toospooky.*")) {
                    event.getMessageAuthor().getConnectedVoiceChannel().ifPresent(voiceChannel ->
                    {
                        if (voiceChannel.canYouConnect() && voiceChannel.canYouSee() && voiceChannel.hasPermission(event.getApi().getYourself(), PermissionType.SPEAK)) {

                            String url = "";
                            if (message.matches("!toospooky.*behind.*")) {
                                url = randomVoiceResponse(SpiritBoxResponse.BEHIND);
                            }
                            else if (message.matches("!toospooky.*die.*")) {
                                url = randomVoiceResponse(SpiritBoxResponse.DIE);
                            }
                            else if (message.matches("!toospooky.*here.*")) {
                                url = randomVoiceResponse(SpiritBoxResponse.HERE);
                            }
                            else if (message.matches("!toospooky.*old.*")) {
                                url = randomVoiceResponse(SpiritBoxResponse.OLD);
                            }
                            else if (message.matches("!toospooky.*kid.*")) {
                                url = randomVoiceResponse(SpiritBoxResponse.KID);
                            }
                            else if (message.matches("!toospooky.*attack.*")) {
                                url = randomVoiceResponse(SpiritBoxResponse.ATTACK);
                            }
                            else if (message.matches("!toospooky.*adult.*")) {
                                url = randomVoiceResponse(SpiritBoxResponse.ADULT);
                            }
                            else if (message.matches("!toospooky.*far.*")) {
                                url = randomVoiceResponse(SpiritBoxResponse.FAR);
                            }
                            else if (message.matches("!toospooky.*close.*")) {
                                url = randomVoiceResponse(SpiritBoxResponse.CLOSE);
                            }
                            else if (message.matches("!toospooky.*away.*")) {
                                url = randomVoiceResponse(SpiritBoxResponse.AWAY);
                            }
                            else if (message.matches("!toospooky.*child.*")) {
                                url = randomVoiceResponse(SpiritBoxResponse.CHILD);
                            }
                            else if (message.matches("!toospooky.*kill.*")) {
                                url = randomVoiceResponse(SpiritBoxResponse.KILL);
                            }
                            else if (message.matches("!toospooky.*next.*")) {
                                url = randomVoiceResponse(SpiritBoxResponse.NEXT);
                            }

                            if (url != null) {
                                final String soundFile = url;
                                SoundManager m = new SoundManager(manager);

                                if (!voiceChannel.isConnected(event.getApi().getYourself()) && !server.getAudioConnection().isPresent()) {

                                    voiceChannel.connect().thenAccept(audioConnection -> {
                                        AudioSource audio = new LavaplayerAudioSource(event.getApi(), m.player);
                                        audioConnection.setAudioSource(audio);
                                        audioConnection.setSelfDeafened(true);
                                        play(soundFile, m);
                                    });

                                    // If the bot is already on a channel, and is playing music.
                                } else if (server.getAudioConnection().isPresent()) {

                                    server.getAudioConnection().ifPresent(audioConnection -> {
                                        if (audioConnection.getChannel().getId() == voiceChannel.getId()) {
                                            AudioSource audio = new LavaplayerAudioSource(event.getApi(), m.player);
                                            audioConnection.setAudioSource(audio);
                                            audioConnection.setSelfDeafened(true);
                                            play(soundFile, m);
                                        } else {
                                            // bot in diff channel
                                        }
                                    });
                                }
                            }
                        }
                    });
                } else {
                    if (message.matches(".*where is.*")) {
                        event.getChannel().sendMessage(randomResponse("where"));
                    }

                    if (message.matches(".*where are.*")) {
                        event.getChannel().sendMessage(randomResponse("where"));
                    }

                    if (message.matches(".*how old.*")) {
                        event.getChannel().sendMessage(randomResponse("old"));
                    }

                    if (message.matches(".*friendly\\?")) {
                        event.getChannel().sendMessage(randomResponse("friendly"));
                    }
                }
            });
        });
    }

    private static String randomResponse(String message) {
        Random r = new Random();
        if (message.equals("where")) {
            int rand = r.nextInt(5) + 1;
            switch(rand) {
                case 1: return "BEHIND";
                case 2: return "CLOSE";
                case 3: return "AWAY";
                case 4: return "HERE";
                case 5: return "FAR";
            }
        }
        if (message.equals("old")) {
            int rand = r.nextInt(4) + 1;
            switch(rand) {
                case 1: return "ADULT";
                case 2: return "CHILD";
                case 3: return "OLD";
                case 4: return "KID";
            }
        }
        if (message.equals("friendly")) {
            int rand = r.nextInt(4) + 1;
            switch(rand) {
                case 1: return "KILL";
                case 2: return "NEXT";
                case 3: return "ATTACK";
                case 4: return "DIE";
            }
        }

        return "ERROR";
    }

    private static String randomVoiceResponse(SpiritBoxResponse message) {
        switch (message) {
            case ADULT: return "https://www.youtube.com/watch?v=wHxLhg50X-k";
            case ATTACK: return "https://www.youtube.com/watch?v=IoGhTwDvGrc";
            case AWAY: return "https://www.youtube.com/watch?v=-T4HXXqUGOQ";
            case BEHIND: return "https://www.youtube.com/watch?v=WYRaFO8jyPM";
            case CHILD: return "https://www.youtube.com/watch?v=tMjV7Tf4fg8";
            case CLOSE: return "https://www.youtube.com/watch?v=r5P3EuFZVlI";
            case DIE: return "https://www.youtube.com/watch?v=fCSqvG5pDEs";
            case FAR: return "https://www.youtube.com/watch?v=VlzKM5he1SM";
            case HERE: return "https://www.youtube.com/watch?v=mi_GOFpAmKI";
            case KID: return "https://www.youtube.com/watch?v=LQmmlMpf1xY";
            case KILL: return "https://www.youtube.com/watch?v=XdI2IrLG-tM";
            case NEXT: return "https://www.youtube.com/watch?v=m_SgiKifu-o";
            case OLD: return "https://www.youtube.com/watch?v=pbuo4vtD1cg";
        }

        return null;
    }

    private static void play(String query, SoundManager m){
        manager.loadItemOrdered(m, query, new FunctionalResultHandler(m.scheduler::queue, audioPlaylist -> {
            if (audioPlaylist.isSearchResult()) {
                m.scheduler.queue(audioPlaylist.getTracks().get(0));
            } else {
                audioPlaylist.getTracks().forEach(m.scheduler::queue);
            }
        }, () -> {

        },
        e -> {
            System.out.println("error");
        }));
    }

}