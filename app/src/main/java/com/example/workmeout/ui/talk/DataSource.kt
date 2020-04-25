package com.example.workmeout.ui.talk

class DataSource{

    companion object{

        fun createDataSet(): ArrayList<DataChat>{
            val list = ArrayList<DataChat>()
            list.add(
                DataChat(
                    "Maria",
                    "maria023",
                    "https://ojfyip2d7m-flywheel.netdna-ssl.com/freshstart/wp-content/uploads/sites/9/2017/01/militza-maury-profile-circle-300x300.png",
                    "Maria, it's okey"
                )
            )
            list.add(
                DataChat(
                    "Pablo",
                    "Pablete1999",
                    "https://mk0trickyphotos51tq5.kinstacdn.com/wp-content/uploads/2017/08/final-1.png",
                    "hahahah I guess"
                )
            )

            list.add(
                DataChat(
                    "Paula",
                    ";)",
                    "https://daringdivasdirectory.com/wp-content/uploads/2016/04/Megan-Walrod-INNER-CIRCLE-Testimonial-300x300.png",
                    "Yeaaaaaah"
                )
            )
            list.add(
                DataChat(
                    "Carlos",
                    "ya sabes quien",
                    "data:image/jpeg;base64,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",
                    "No, it is going to be impossible :("
                )
            )
            list.add(
                DataChat(
                    "Pascual",
                    "android 4 ever",
                    "https://lh3.googleusercontent.com/proxy/iQBOOa6A-XOwM9tsf8oJW6fid888N_G3mr_hE3119EKyj7ZSM0grGZkUz4h49HOn8Pzrko6g9NUfysIdOzqf-cr_AAtRuebhRzzivYmrmI-U0WHmW6Ys9NmJjbkJ76o3qibcmbLRoACggiveIsy-ocfgMg",
                    "Richelle"
                )
            )
            list.add(
                DataChat(
                    "Eugenia",
                    "software developer",
                    "https://images.squarespace-cdn.com/content/v1/593c43df197aeae4e205ce0e/1547649285153-UH36K30RLM418UIWDC7F/ke17ZwdGBToddI8pDm48kKZ5jBckBVr9em4AjUOKwF9Zw-zPPgdn4jUwVcJE1ZvWQUxwkmyExglNqGp0IvTJZUJFbgE-7XRK3dMEBRBhUpxBNySLVDlkniC4jtdikcfk_0RR7Fgj6ZirOm-RNT2eZnJ7DbiWaP5t7kScxnHVXYs/Shift+Headshot+-+Rasha+%28circle%29+%281%29.png",
                    "Maria is the best. Have you heard from her lately?"
                )
            )
            list.add(
                DataChat(
                    "Carmen",
                    "Carmeeeen",
                    "https://www.seekpng.com/png/full/627-6279537_circle-invest-welcomes-monero-portrait-circle.png",
                    "I'm asking Alfonso about Maria"
                )
            )
            list.add(
                DataChat(
                    "Jeremias",
                    "Jeremias96",
                    "https://darwins-circle.com/wp-content/uploads/2019/08/Xiao_WP.png",
                    "Keep Calm hahahah"
                )
            )
            list.add(
                DataChat(
                    "Nacho",
                    "NachoM",
                    "https://thecontentdivision.com.au/wp-content/uploads/2017/08/mark-circle.png",
                    "Okey"
                )
            )
            return list
        }
    }
}