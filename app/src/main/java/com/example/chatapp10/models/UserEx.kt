package com.example.chatapp10.models

class UserEx {
    var email: String? = null
    var displayName: String? = null
    var photoUrl: String? = null
    var uid: String? = null

    constructor(email: String?, displayName: String?, photoUrl: String?, uid: String?) {
        this.email = email
        this.displayName = displayName
        this.photoUrl = photoUrl
        this.uid = uid
    }

    constructor()

}