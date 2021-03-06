package facebookoauth

import grails.converters.JSON
import org.scribe.model.Token

class OauthCallbackController {

    def oauthService

    def index() {}

    def success() {
        Token facebookAccessToken = (Token) session[oauthService.findSessionKeyForAccessToken('facebook')]
        if (facebookAccessToken) {
            def facebookResource = oauthService.getFacebookResource(facebookAccessToken, "https://graph.facebook.com/me")
			
            def facebookResponse = JSON.parse(facebookResource?.getBody())
            //render facebookResponse.picture
            Map data = [id: facebookResponse.id, username: facebookResponse.username, name: facebookResponse.name, email: facebookResponse.email,
                    first_name: facebookResponse.first_name, last_name: facebookResponse.last_name, birthday: facebookResponse.birthday,
                    gender: facebookResponse.gender, link: facebookResponse.link, work: facebookResponse.work, hometown: facebookResponse.hometown,
                    education: facebookResponse.education , picture: 'https://graph.facebook.com/'+ facebookResponse.id +'/picture?width=440&height=440' ]

            data.each { 
				
				render it 
				render  "<br>"
			}
			
        } else {
            flash.error = "Token not found."
            render view: '/index'
        }
    }

    def error() {
        render params
    }
}
