package Game.Sense.client.utils.render;

        import net.minecraft.client.renderer.GlStateManager;
        import org.lwjgl.opengl.GL11;
        import org.lwjgl.opengl.GL20;

        import java.io.ByteArrayOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.nio.charset.StandardCharsets;

        import static org.lwjgl.opengl.GL20.*;

public class GLSLSandboxShader {
    private final int programId;
    private final int timeUniform;
    private final int mouseUniform;
    private final int resolutionUniform;

    public GLSLSandboxShader(String fragmentShaderLocation) throws IOException {
        int program = glCreateProgram();

        glAttachShader(program, createShader(GLSLSandboxShader.class.getResourceAsStream("/passthrough.vsh"), GL_VERTEX_SHADER));
        glAttachShader(program, createShader(GLSLSandboxShader.class.getResourceAsStream(fragmentShaderLocation), GL_FRAGMENT_SHADER));

        glLinkProgram(program);

        int linked = glGetProgrami(program, GL_LINK_STATUS);

        // Если хуйня шейдер не коннект
        if (linked == 0) {
            System.err.println(glGetProgramInfoLog(program, glGetProgrami(program, GL_INFO_LOG_LENGTH)));

            throw new IllegalStateException("Shader failed to link");
        }

        this.programId = program;

        // Нужные штуки ок да
        glUseProgram(program);

        this.timeUniform = glGetUniformLocation(program, "time");
        this.mouseUniform = glGetUniformLocation(program, "mouse");
        this.resolutionUniform = glGetUniformLocation(program, "resolution");

        glUseProgram(0);
    }
    // основная функция данной утилиты
    public void useShader(int width, int height, float mouseX, float mouseY, float time) {
        GL20.glUseProgram(programId);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL20.glUniform2f(resolutionUniform, (float)width, (float)height);
        GL20.glUniform2f(mouseUniform, mouseX / (float)width, 1.0f - mouseY / (float)height);
        GL20.glUniform1f(timeUniform, time);
        ShaderUtil.drawQuads(mouseX, mouseY, width, height);
        GlStateManager.disableBlend();
    }
    // Тут функция создает шейдер ок да
    private int createShader(InputStream inputStream, int shaderType) throws IOException {
        int shader = glCreateShader(shaderType);

        glShaderSource(shader, readStreamToString(inputStream));

        glCompileShader(shader);

        int compiled = glGetShaderi(shader, GL_COMPILE_STATUS);

        // Если хуйня шейдер не коннект
        if (compiled == 0) {
            System.err.println(glGetShaderInfoLog(shader, glGetShaderi(shader, GL_INFO_LOG_LENGTH)));

            throw new IllegalStateException("Failed to compile shader");
        }

        return shader;
    }
    // Нужная хуйня функция которая читает fsh файлы
    private String readStreamToString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        byte[] buffer = new byte[512];

        int read;

        while ((read = inputStream.read(buffer, 0, buffer.length)) != -1) {
            out.write(buffer, 0, read);
        }

        return new String(out.toByteArray(), StandardCharsets.UTF_8);
    }
}


