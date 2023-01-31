package me.nightproject.net.welcomenight.firstjoin.traveller;

import dev.lone.itemsadder.api.CustomStack;
import me.nightproject.net.welcomenight.WelcomeNight;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.JSONException;
import org.json.simple.JSONObject;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public class FirstJoin implements Listener {

    private static final Set<UUID> MessageSet = new HashSet<>();
    private static final Set<UUID> Message2Set = new HashSet<>();

    private static final Set<UUID> Message3Set = new HashSet<>();


    private void readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        }
    }

    public void request(){

        try {
            readJsonFromUrl("https://maker.ifttt.com/trigger/nightcity_bordello/json/with/key/gjx_uhlrxQLctHyrd5gEcG2rX7ReZ19eWZI3ICBKmzj");
            readJsonFromUrl("https://api.telegram.org/bot5844873639:AAERALgY60HTMKxHRBLa8os4P6ZbUMvRanE/sendMessage?chat_id=" + "-1001664073717" + "&text=<b>Night | Security</b>%0A%0A<b>Porco dio è successo qualcosa controllate i log urgenti su discord.</b> <i></i>&parse_mode=HTML");
        } catch (IOException a) {
            a.printStackTrace();
        }

    }


    @EventHandler
    public void onPlayerIteractNpc(PlayerInteractAtEntityEvent e) {

        Player player = e.getPlayer();

        ItemStack biglietto = CustomStack.getInstance("itemsadder:black_coupon").getItemStack();
        ItemMeta bigliettoMeta = biglietto.getItemMeta();
        bigliettoMeta.setDisplayName("§fBiglietto Partenza");
        bigliettoMeta.setLore(Arrays.asList("§7Biglietto Imbarco per §fNightCity§7!!"));
        biglietto.setItemMeta(bigliettoMeta);


        if (!(e.getRightClicked() instanceof Player)) {
            return;
        }

        if (e.getRightClicked() == null) {
            return;
        }


        Player player1 = (Player) e.getRightClicked();

        if (e.getHand() == null || !e.getHand().equals(EquipmentSlot.HAND)) {
            return;
        }

        if (!(player1.getName().equalsIgnoreCase("Owen"))) {
            return;
        }

        if (!MessageSet.contains(player.getUniqueId())) {
            player.sendMessage("§9♂ §7● §fOwen §8» §7Ciao, sei pronto per prendere il primo volo per NightCity??");
            MessageSet.add(player.getUniqueId());

            return;
        }

        if (WelcomeNight.getEconomy().getBalance(player.getName()) > 20000) {



            Date now = new Date();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            if (Message3Set.contains(player.getUniqueId())) {
                return;
            }

            Message3Set.add(player.getUniqueId());
            Bukkit.getScheduler().runTaskAsynchronously(WelcomeNight.getMain(), () -> {
                EmbedBuilder eb = new EmbedBuilder()
                        .setTitle(
                                "\ud83d\udcdd  QUALCUNO CERCA DI FREGARCI  \ud83d\udcdd\n\n" +
                                        String.format("\ud83d\udca1  Cittadino: %s\n", player.getName()) +
                                        String.format("\ud83d\udca1  Motivo: %s\n", "Ha provato a richiedere altri 50k iniziali!") +
                                        String.format("\ud83d\udcf0  il giorno: %s\n", format.format(now))

                        )
                        .setColor(new Color(83, 205, 93));
                TextChannel channel = WelcomeNight.getJDA().getTextChannelById("1047840262904369202");
                if (channel == null) throw new IllegalStateException("channel == null");
                channel.sendMessage(eb.build()).queue();
            });

            request();

            Bukkit.getScheduler().runTaskLater(WelcomeNight.getMain(),() -> {
                Message3Set.remove(player.getUniqueId());
            },2000);







            return;
        }

        if (!Message2Set.contains(player.getUniqueId())) {

            Message2Set.add(player.getUniqueId());


            Bukkit.getScheduler().runTaskLater(WelcomeNight.getMain(), () -> {
                player.sendMessage("§9♂ §7● §fOwen §8» §7Ricorda di non portare nulla di §cIllegale §7nei bagagli!!");


                Bukkit.getScheduler().runTaskLater(WelcomeNight.getMain(), () -> {
                    player.sendMessage("§9♂ §7● §fOwen §8» §7Ah giusto!! Tieni questi ti serviranno per seguire il tuo §6Sogno Americano§7!!");


                    Bukkit.getScheduler().runTaskLater(WelcomeNight.getMain(), () -> {


                        player.sendMessage("§9♂ §7● §fOwen §8» §7Buona fortuna §f" + player.getName() + "§7!!");
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1);
                        WelcomeNight.getEconomy().depositPlayer(player.getName(), 20000);
                        player.getInventory().addItem(biglietto);
                        Message3Set.add(player.getUniqueId());

                        Bukkit.getScheduler().runTaskAsynchronously(WelcomeNight.getMain(), () -> {
                            EmbedBuilder eb = new EmbedBuilder()
                                    .setTitle(
                                            "\ud83d\udcdd  SOLDI INIZIALI RICEVUTI  \ud83d\udcdd\n\n" +
                                                    String.format("\ud83d\udca1  Cittadino: %s\n", player.getName())
                                    )
                                    .setColor(new Color(83, 205, 93));
                            TextChannel channel = WelcomeNight.getJDA().getTextChannelById("1047840262904369202");
                            if (channel == null) throw new IllegalStateException("channel == null");
                            channel.sendMessage(eb.build()).queue();
                            channel.pinMessageById(channel.getLatestMessageId());
                        });




                    }, 50);


                }, 50);


            }, 50);


        }
    }




}
