import * as THREE from 'three';
import { GLTFLoader } from 'three/addons/loaders/GLTFLoader.js';
import { OrbitControls } from 'three/addons/controls/OrbitControls.js';

const loader = new GLTFLoader();
var controls;

// Load model 
loader.load('ISS_stationary.glb',
function (gltf) {
    const renderer = new THREE.WebGLRenderer();
    renderer.setSize(window.innerWidth, window.innerHeight);
    renderer.outputColorSpace = THREE.SRGBColorSpace;
    renderer.setPixelRatio(window.devicePixelRatio);

    const scene = new THREE.Scene();
    scene.add(gltf.scene);

    const light = new THREE.AmbientLight(0xf7f7f5);
    scene.add(light);

    const camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 1, 1000);
    // Set standart position of the camera
    camera.position.set(10, 10, 80);

    const axesHelper = new THREE.AxesHelper(5);
    scene.add(axesHelper)

    // Set OrbitControls, to move the model in the scene
    controls = new OrbitControls(camera, renderer.domElement);
    controls.autoRotate = true;
    controls.autoRotateSpeed = 0.5;

    function animate() {
        requestAnimationFrame(animate);
        renderer.render(scene, camera);
    }

    document.body.appendChild(renderer.domElement);
    animate();
}, function (xhr) {
    console.log((xhr.loaded / xhr.total * 100) + '% loaded');
}, function (error) {
    console.error(error);
});