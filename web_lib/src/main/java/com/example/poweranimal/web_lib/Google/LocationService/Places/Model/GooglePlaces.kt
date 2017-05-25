package com.example.poweranimal.web_lib.Google.LocationService.Places.Model

import com.example.poweranimal.web_lib.Google.LocationService.Places.Thread.PlacesManager

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder
import java.net.URL

/**
 * Created by felixproehl on 05.05.17.
 */

/**
 * POJO that holds all information from a [PlacesManager]-web request.
 *
 * @see <a href="https://developers.google.com/places/web-service/search">Google Search Places</a>
 */
data class GooglePlaces(
        @SerializedName("next_page_token") @Expose val nextPageToken: String,
        @SerializedName("results") @Expose val results: List<Result>,
        @SerializedName("status") @Expose val status: String,
        @SerializedName("error_message") @Expose val errorMessage: String,
        @SerializedName("html_attributions") @Expose val htmlAttributions: Array<String>)
{
    override fun equals(other: Any?): Boolean {

        if (this === other) return true

        if (other?.javaClass != javaClass) return false

        other as GooglePlaces

        return EqualsBuilder()
                .append(nextPageToken, other.nextPageToken)
                .append(results, other.results)
                .append(status, other.results)
                .append(errorMessage, other.errorMessage)
                .append(htmlAttributions, other.htmlAttributions)
                .isEquals
    }

    override fun hashCode(): Int {
        return HashCodeBuilder()
                .append(nextPageToken)
                .append(results)
                .append(status)
                .append(errorMessage)
                .append(htmlAttributions)
                .toHashCode()
    }
}

data class Result (
        @SerializedName("geometry") @Expose val geometry: Geometry,
        @SerializedName("icon") @Expose val icon: URL,
        @SerializedName("id") @Expose val id: String,
        @SerializedName("name") @Expose val name: String,
        @SerializedName("opening_hours") @Expose val openingHours: OpeningHours,
        @SerializedName("photos") @Expose val photos: Array<Photo>,
        @SerializedName("place_id") @Expose val place_id: String,
        @SerializedName("scope") @Expose val scope: String,
        @SerializedName("alt_ids") @Expose val altIds: Array<AltId>,
        @SerializedName("reference") @Expose val reference: String,
        @SerializedName("types") @Expose val types: Array<String>,
        @SerializedName("vicinity") @Expose val vicinity: String,
        @SerializedName("price_level") @Expose val priceLevel: Int,
        @SerializedName("rating") @Expose val rating: Double,
        @SerializedName("formatted_address") @Expose val formattedAddress: String,
        @SerializedName("permanently_closed") @Expose val permanentlyClosed: Boolean)
{
    override fun equals(other: Any?): Boolean {

        if (this === other) return true

        if (other?.javaClass != javaClass) return false

        other as Result

        return EqualsBuilder()
                .append(geometry, other.geometry)
                .append(icon, other.icon)
                .append(id, other.id)
                .append(name, other.name)
                .append(openingHours, other.openingHours)
                .append(photos, other.photos)
                .append(place_id, other.place_id)
                .append(scope, other.scope)
                .append(altIds, other.altIds)
                .append(reference, other.reference)
                .append(types, other.types)
                .append(vicinity, other.vicinity)
                .append(priceLevel, other.priceLevel)
                .append(rating, other.rating)
                .append(formattedAddress, other.formattedAddress)
                .append(permanentlyClosed, other.permanentlyClosed)
                .isEquals
    }

    override fun hashCode(): Int {
        return HashCodeBuilder()
                .append(geometry)
                .append(icon)
                .append(id)
                .append(name)
                .append(openingHours)
                .append(photos)
                .append(place_id)
                .append(scope)
                .append(altIds)
                .append(reference)
                .append(types)
                .append(vicinity)
                .append(priceLevel)
                .append(rating)
                .append(formattedAddress)
                .append(permanentlyClosed)
                .toHashCode()
    }
}

data class OpeningHours (
        @SerializedName("open_now") @Expose val openNow: Boolean)
{
    override fun equals(other: Any?): Boolean {

        if (this === other) return true

        if (other?.javaClass != javaClass) return false

        other as OpeningHours

        return EqualsBuilder()
                .append(openNow, other.openNow)
                .isEquals
    }

    override fun hashCode(): Int {
        return HashCodeBuilder()
                .append(openNow)
                .toHashCode()
    }
}

data class AltId (
        @SerializedName("place_id") @Expose val placeId: String,
        @SerializedName("scope") @Expose val scope: String)
{
    override fun equals(other: Any?): Boolean {

        if (this === other) return true

        if (other?.javaClass != javaClass) return false

        other as AltId

        return EqualsBuilder()
                .append(placeId, other.placeId)
                .append(scope, other.scope)
                .isEquals
    }

    override fun hashCode(): Int {
        return HashCodeBuilder()
                .append(placeId)
                .append(scope)
                .toHashCode()
    }
}

data class Geometry (
        @SerializedName("location") @Expose val location: LatLng)
{
    override fun equals(other: Any?): Boolean {

        if (this === other) return true

        if (other?.javaClass != javaClass) return false

        other as Geometry

        return EqualsBuilder()
                .append(location, other.location)
                .isEquals
    }

    override fun hashCode(): Int {
        return HashCodeBuilder()
                .append(location)
                .toHashCode()
    }
}

data class Photo (
        @SerializedName("width") @Expose val width: Int,
        @SerializedName("height") @Expose val height: Int,
        @SerializedName("html_attributions") @Expose val htmlAttributions: Array<String>,
        @SerializedName("photo_reference") @Expose val photoReference: String)
{
    override fun equals(other: Any?): Boolean {

        if (this === other) return true

        if (other?.javaClass != javaClass) return false

        other as Photo

        return EqualsBuilder()
                .append(width, other.width)
                .append(height, other.height)
                .append(htmlAttributions, other.htmlAttributions)
                .append(photoReference, other.photoReference)
                .isEquals
    }

    override fun hashCode(): Int {
        return HashCodeBuilder()
                .append(width)
                .append(height)
                .append(htmlAttributions)
                .append(photoReference)
                .toHashCode()
    }
}
