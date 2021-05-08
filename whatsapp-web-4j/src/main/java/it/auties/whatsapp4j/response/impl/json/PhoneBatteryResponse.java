package it.auties.whatsapp4j.response.impl.json;

import it.auties.whatsapp4j.response.model.json.JsonResponseModel;

/**
 * A json model that contains information the battery status of the phone associated with this session
 *
 * @param value     an unsigned int that represents how much battery percentage is left on the phone
 * @param live      a flag to determine whether this update is in real time or not???
 * @param powerSave a flag to determine whether the phone is on battery save mode or not
 */
public final record PhoneBatteryResponse(int value, boolean live, boolean powerSave) implements JsonResponseModel {
}
