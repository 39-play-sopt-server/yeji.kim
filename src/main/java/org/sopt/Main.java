package org.sopt;

import org.sopt.post.PostController;
import org.sopt.post.PostView;

public class Main {

    public static void main(String[] args) {
        PostView view = new PostView();
        PostController controller = new PostController(view);
        controller.run();
    }
}
