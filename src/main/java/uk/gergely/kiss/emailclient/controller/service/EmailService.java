package uk.gergely.kiss.emailclient.controller.service;

import org.json.JSONObject;

public interface EmailService {

    JSONObject process(org.json.simple.JSONObject request);
}
