package models;

public record Notification(Friend friend, String message, byte[] profileIcon, Long timestamp) { }
