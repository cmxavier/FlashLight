package com.xavier.christian.myirishflashlight;

        import android.app.Activity;
        import android.hardware.Camera;
        import android.hardware.Camera.Parameters;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;

public class FlashLight extends Activity {
    private final static String LOG_TAG = "FlashLight";

    private Button mOnBtn;
    private Button mOffBtn;
    private boolean actionBtn;

    private Camera mCamera;

    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Atribui o layout
        setContentView(R.layout.activity_flash_light);

        //Recupera o Botão de ON
        mOnBtn = (Button) findViewById(R.id.on_btn);

        view = findViewById(R.id.layoutlight);

        // Atribui a função de click

        mOnBtn.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View v) {
                if(actionBtn ==false) {

                    view.setBackground(getResources().getDrawable(R.drawable.lighton));
                    processOnClick();

                    mOnBtn.setBackgroundResource(R.drawable.poweron);




                }else{
                    view.setBackground(getResources().getDrawable(R.drawable.lightoff));
                    processOffClick();
                    mOnBtn.setBackgroundResource(R.drawable.poweroff);


                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        try{
            // Abre a comunicação com a Camera
            mCamera = Camera.open();
        } catch( Exception e ){
            Log.e(LOG_TAG, "Não foi possivel conectar a Câmera");
        }
    }

    @Override
    protected void onPause() {
        if( mCamera != null ){
            // Libera o uso da Camera
            mCamera.release();
            mCamera = null;
        }
        super.onPause();
    }

    private void processOffClick(){
        // Desliga a Camera
        if( mCamera != null ){
            Parameters params = mCamera.getParameters();
            params.setFlashMode( Parameters.FLASH_MODE_OFF );
            mCamera.setParameters( params );
            actionBtn =false;
        }
    }

    private void processOnClick(){
        // Liga a Camera
        if( mCamera != null ){
            Parameters params = mCamera.getParameters();
            params.setFlashMode( Parameters.FLASH_MODE_TORCH );
            mCamera.setParameters( params );

            actionBtn =true;
        }
    }
}