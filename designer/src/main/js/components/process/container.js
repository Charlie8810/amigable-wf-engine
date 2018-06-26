import React, { Component } from 'react';
import ProcessList from './process-list'

const Container = (props) => (

  <div className="App">
    <div className="section no-pad-bot">
        <div className="container">
            <h4>Listado de Procesos</h4>
            <ProcessList data={props.data} />
        </div>
    </div>


    <div className="container"></div>

  </div>

)

export default Container;